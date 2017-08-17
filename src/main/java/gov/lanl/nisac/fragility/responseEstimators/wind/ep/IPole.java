package gov.lanl.nisac.fragility.responseEstimators.wind.ep;

public interface IPole {

    /**
     * @return base area (m^2)
     */
    double baseArea();

    /**
     * @return the baseDiameter (m)
     */
    double getBaseDiameter();

    /**
     * @return mean pole strength (Pa)
     */
    double getMeanPoleStrength();

    /**
     * @return standard deviation of pole strength (Pa)
     */
    double getStdDevPoleStrength();

    /**
     * @return Z_bott (m^3)
     */
    double getZ_bott();

    /**
     * @return pole mass (kg)
     */
    double mass();

}