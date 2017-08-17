package gov.lanl.nisac.fragility.responseEstimators.wind.ep;

public class Pole implements IPole {
    private static final double _32_0 = 32.0;

    private static final double PI_O_4 = Math.PI / 4.0;

    private static double SQ(double d) {
        return d * d;
    }

    private final double baseDiameter;
    private final double height;
    private final double topDiameter;
    private final double woodDensity;
    private final double meanPoleStrength;
    private final double stdDevPoleStrength;

    /**
     * @param baseDiameter (m)
     * @param topDiameter (m)
     * @param height (m)
     * @param woodDensity (kg/m^3)
     * @param meanPoleStrength (Pa)
     * @param stdDevPoleStrength (Pa)
     */
   public Pole(double baseDiameter, double topDiameter, double height, double woodDensity,
            double meanPoleStrength, double stdDevPoleStrength) {
        //super();
        this.baseDiameter = baseDiameter;
        this.topDiameter = topDiameter;
        this.height = height;
        this.woodDensity = woodDensity;
        this.meanPoleStrength = meanPoleStrength;
        this.stdDevPoleStrength = stdDevPoleStrength;
    }

    public double baseArea() {
        return PI_O_4 * SQ(baseDiameter);
    }


    public double getBaseDiameter() {
        return baseDiameter;
    }


    public double getMeanPoleStrength() {
        return meanPoleStrength;
    }


    public double getStdDevPoleStrength() {
        return stdDevPoleStrength;
    }


    public double getZ_bott() {
        return Math.PI * Math.pow(baseDiameter, 3) / _32_0;
    }


    public double mass() {
        return PI_O_4 * (SQ(baseDiameter) + SQ(topDiameter)) / 2 * height * woodDensity;
    }

}
