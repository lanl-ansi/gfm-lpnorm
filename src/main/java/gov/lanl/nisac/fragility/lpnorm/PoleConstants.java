package gov.lanl.nisac.fragility.lpnorm;

public final class PoleConstants {

    private PoleConstants() {
    }

    // Asset Attributes
    static final String ASSET_CLASS = "PowerDistributionPole";
    static final String GEOMETRY_TYPE = "Point";

    static final float BASE_DIAMETER = 0.2222f;
    static final float CABLE_SPAN = 90.0f;
    static final float COMM_ATTACHMENT_HEIGHT = 4.7244f;
    static final float COMM_CABLE_DIAMETER = 0.04f;
    static final int COMM_CABLE_NUMBER = 2;
    static final float COMM_CABLE_WIRE_DENSITY = 2700.0f;
    static final float HEIGHT = 9.144f;
    static final double MEAN_POLE_STRENGTH = 3.96e7;
    static final float POWER_ATTACHMENT_HEIGHT = 5.6388f;
    static final float POWER_CABLE_DIAMETER = 0.0094742f;
    static final int POWER_CABLE_NUMBER = 3;
    static final String POWER_CIRCUIT_NAME = "GFM-generated";
    static final float POWER_CABLE_WIRE_DENSITY = 2700.0f;

    static final double STD_DEV_POLE_STRENGTH = 7700000.0;
    static final float TOP_DIAMETER = 0.153616f;
    static final double WOOD_DENSITY = 500.0;

    // hazard field attributes
    static final String HAZ_ID = "GFM-generated-ID";
    static final String HAZARD_QUANTITY_TYPE = "Windspeed";
    static final String HAZ_GRID_FORMAT = "ArcGrid";
    static final String HAZ_CRS_CODE = "ArcGrid";
    static final int HAZ_NUMBER_OF_BANDS = 1;
    static final int HAZ_RASTER_BAND = 1;
    static final String HAZ_VALUE_TYPE = "double";

    // response estimators
    static final String RSP_ID = "PowerPoleWindStressEstimator";
    static final String RSP_ESTIMATOR_CLASS = "PowerPoleWindStressEstimator";
    static final String RSP_ASSET_CLASS = "PowerDistributionPole";
    static final String RSP_HAZ_QUANTITY_TYPE = "Windspeed";
    static final String RSP_QUANTITY_TYPE = "DamageProbability";

    // pole spacing - meters
    static final double POLE_SPACING = 91.0;

    // next pole at 91 meters 0.000817463169242 degrees
    static final double NEXT_DISTANCE = 0.000817463169242;

    // using 111.111 km per degree (or 111,111 meters)
    static final double DEG_TO_METERS = 111111.0;

}
