package gov.lanl.nisac.fragility.responseEstimators.wind.ep;

public interface IDistributionLine {

    /**
     * @return the cableSpan (m)
     */
    double getCableSpan();

    /**
     * @return the commCable
     */
    ICable getCommCable();

    /**
     * @return the pole
     */
    IPole getPole();

    /**
     * @return the powerCable
     */
    ICable getPowerCable();

}