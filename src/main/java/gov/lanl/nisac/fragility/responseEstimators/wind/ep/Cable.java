package gov.lanl.nisac.fragility.responseEstimators.wind.ep;

public class Cable implements ICable {
	static final double PI_O_4 = Math.PI / 4.0;

	static double SQ(double d) {
		return d * d;
	}

	private final double attachmentHeight;
	private final double diameter;
	private final int number;
	private final double wireDensity;

	/**
	 * @param diameter (m)
	 * @param wireDensity (kg/m^3)
	 * @param attachmentHeight (m)
	 * @param number (-)
	 */
	public Cable(double diameter, double wireDensity, double attachmentHeight,
			int number) {
		super();
		this.diameter = diameter;
		this.wireDensity = wireDensity;
		this.attachmentHeight = attachmentHeight;
		this.number = number;
	}

	@Override
    public double getAttachmentHeight() {
		return attachmentHeight;
	}

	@Override
    public double iceVolume(double span, double iceRadialThickness) {
		final double iceArea = PI_O_4
				* (SQ(diameter + 2.0 * iceRadialThickness) - SQ(diameter));
		return iceArea * span * number;
	}

	@Override
    public double mass(double span) {
		return PI_O_4 * SQ(diameter) * span * number * wireDensity;
	}

	@Override
    public double plusIceTotalArea(double iceRadialThickness, double span) {
		return number * (diameter + 2 * iceRadialThickness) * span;
	}

}
