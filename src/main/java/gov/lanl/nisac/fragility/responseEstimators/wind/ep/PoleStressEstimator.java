package gov.lanl.nisac.fragility.responseEstimators.wind.ep;

public class PoleStressEstimator {
	private static final double G = 9.81;

	static double SQ(double d) {
		return d * d;
	}

	private final ICable commCable;
	private final IPole pole;
	private final ICable powerCable;
    private final double cableSpan;

	/**
	 * @param distLine
	 * @param cableSpan 
	 */
	public PoleStressEstimator(IDistributionLine distLine, double cableSpan) {
		super();
		this.pole = distLine.getPole();
		this.cableSpan = cableSpan;
		this.powerCable = distLine.getPowerCable();
		this.commCable = distLine.getCommCable();
	}

	/**
	 * @param iceRadialThickness (m)
	 * @param windSpeed (m/s)
	 * @return
	 */
	public double commCablePlusIceExtremeFiberTensileStress(double iceRadialThickness, double windSpeed) {
		return cablePlusIceWindMoment(commCable, iceRadialThickness, windSpeed) / pole.getZ_bott();
	}

	/**
	 * @return
	 */
	public double compressiveStress() {
		return totalDeadLoad() / pole.getBaseDiameter();
	}

	/**
     * @return the commCable
     */
    public ICable getCommCable() {
        return commCable;
    }

	/**
     * @return the pole
     */
    public IPole getPole() {
        return pole;
    }

	/**
     * @return the powerCable
     */
    public ICable getPowerCable() {
        return powerCable;
    }

	/**
     * @param iceRadialThickness (m)
	 * @return
	 */
	public double iceCompressiveStress(double iceRadialThickness) {
		return iceWeight(iceRadialThickness) / pole.baseArea();
	}

	/**
	 * @param iceRadialThickness (m)
	 * @param windSpeed (m/s)
	 * @return
	 */
	public double maxTensileStrength(double iceRadialThickness, double windSpeed) {
		return powerCablePlusIceExtremeFiberTensileStress(iceRadialThickness, windSpeed)
				+ commCablePlusIceExtremeFiberTensileStress(iceRadialThickness, windSpeed)
				- iceCompressiveStress(iceRadialThickness) - compressiveStress();
	}

	/**
	 * @param iceRadialThickness (m)
	 * @param windSpeed (m/s)
	 * @return
	 */
	public double powerCablePlusIceExtremeFiberTensileStress(double iceRadialThickness, double windSpeed) {
		return cablePlusIceWindMoment(powerCable, iceRadialThickness, windSpeed) / pole.getZ_bott();
	}

	/**
	 * @param windSpeed (m/s)
	 * @return
	 */
	public double windOnCableDynamicPressure(double windSpeed) {
		return 0.5*airDensity()*SQ(windSpeed);
	}

	private double airDensity() {
		return MaterialParameters.AIR_DENSITY;
	}

	private double cablePlusIceWindForce(ICable cable, double iceRadialThickness, double windSpeed) {
		return cable.plusIceTotalArea(iceRadialThickness, cableSpan) * windOnCableDynamicPressure(windSpeed);
	}

	private double cablePlusIceWindMoment(ICable cable, double iceRadialThickness, double windSpeed) {
		return cablePlusIceWindForce(cable, iceRadialThickness, windSpeed) * cable.getAttachmentHeight();
	}

	private double commCableWeight() {
		return commCable.mass(cableSpan) * G;
	}

    private double iceDensity() {
		return MaterialParameters.ICE_DENSITY;
	}

    private double iceMass(double iceRadialThickness) {
		final double powerCableIceVolume = powerCable.iceVolume(cableSpan, iceRadialThickness);
		final double commCableIceVolume = commCable.iceVolume(cableSpan, iceRadialThickness);
		final double totalIceVolume = powerCableIceVolume + commCableIceVolume;
		return totalIceVolume * iceDensity();
	}

    private double iceWeight(double iceRadialThickness) {
		return iceMass(iceRadialThickness) * G;
	}

    private double poleWeight() {
		return pole.mass() * G;
	}

	private double powerCableWeight() {
		return powerCable.mass(cableSpan) * G;
	}

    private double totalDeadLoad() {
		return poleWeight() + powerCableWeight() + commCableWeight();
	}
}
