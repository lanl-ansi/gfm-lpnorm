package gov.lanl.nisac.fragility.responseEstimators.wind.ep;

import org.apache.commons.math3.distribution.NormalDistribution;

public class PoleFailure {
	private final NormalDistribution normDistPoleFailureVsStress;
	private final PoleStressEstimator poleStressEstimator;
	
	/**
	 * @param cableSpan (m)
     */
	public PoleFailure(IDistributionLine distLine, double cableSpan) {
		this.poleStressEstimator = new PoleStressEstimator(distLine, cableSpan);
		this.normDistPoleFailureVsStress = new NormalDistribution(distLine.getPole().getMeanPoleStrength(), distLine.getPole().getStdDevPoleStrength());
	}

    /**
	 * @param stress (Pa)
	 * @return normDistPoleFailureVsStress cumulative distribution
	 */
	public double cumulativeProbability(double stress) {
		return normDistPoleFailureVsStress.cumulativeProbability(stress);
	}
	
	/**
	 * @param iceRadialThickness (m)
	 * @param windSpeed (m/s)
	 * @return
	 */
	public double cumulativeProbability(double iceRadialThickness, double windSpeed) {
		final double maxTensileStrength = poleStressEstimator.maxTensileStrength(iceRadialThickness, windSpeed);
		//System.out.println("maxTensileStrength: " + maxTensileStrength);
		return cumulativeProbability(maxTensileStrength);
	}
	
	/**
	 * @param stress (Pa)
	 * @return normDistPoleFailureVsStress density stress
	 */
	public double probabiblityDensityFunction(double stress) {
		return normDistPoleFailureVsStress.density(stress);
	}
	
	/**
	 * @param iceRadialThickness (m)
	 * @param windSpeed (m/s)
	 * @return
	 */
	public double probabiblityDensityFunction(double iceRadialThickness, double windSpeed) {
		return normDistPoleFailureVsStress.density(poleStressEstimator.maxTensileStrength(iceRadialThickness, windSpeed));
	}
}
