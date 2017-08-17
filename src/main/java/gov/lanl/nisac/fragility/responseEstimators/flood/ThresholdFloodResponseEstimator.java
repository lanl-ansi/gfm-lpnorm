package gov.lanl.nisac.fragility.responseEstimators.flood;

import gov.lanl.nisac.fragility.assets.IAsset;
import gov.lanl.nisac.fragility.core.IProperty;
import gov.lanl.nisac.fragility.exposure.IExposureEvaluator;
import gov.lanl.nisac.fragility.exposure.PointExposureEvaluator;
import gov.lanl.nisac.fragility.gis.Feature;
import gov.lanl.nisac.fragility.gis.IFeatureCollection;
import gov.lanl.nisac.fragility.gis.IField;
import gov.lanl.nisac.fragility.hazards.IHazardField;
import gov.lanl.nisac.fragility.responseEstimators.AbstractResponseEstimator;
import gov.lanl.nisac.fragility.responseModels.IResponse;
import gov.lanl.nisac.fragility.responseModels.Response;

import java.util.Arrays;
import java.util.Collection;

public class ThresholdFloodResponseEstimator extends AbstractResponseEstimator {

    private final double thresholdDepth;

    public ThresholdFloodResponseEstimator(String id,
                                           String responseEstimatorClass, String assetClass,
                                           double thresholdDepth) {
        super(id, responseEstimatorClass, assetClass, Arrays.asList(new String[]{"FloodDepth"}),
                "FailureProbability");
        this.thresholdDepth = thresholdDepth;
    }

    @Override
    public IResponse getResponse(IAsset asset, Collection<IHazardField> hazardFields)
            throws IllegalArgumentException {
        if (!getAssetClass().equals(asset.getAssetClass())) {
            throw new IllegalArgumentException(
                    String.format("Asset is not of Asset Class %s", getAssetClass()));
        }

        String hazardQuantityType = this.getHazardQuantityTypes().get(0);
        IHazardField hazardField = getHazardField(hazardFields, hazardQuantityType);
        IField field = hazardField.getField();
        IExposureEvaluator exposureEvaluator = new PointExposureEvaluator();

        IFeatureCollection evaluateExposure = exposureEvaluator.evaluateExposure(
                asset.getGeometry(), field);
        Feature exposure = evaluateExposure.getFeature(0);
        IProperty exposureProperty = exposure.getProperty("exposure");
        double depth = (Double) exposureProperty.getValue();
        // The response class is DamageProbability.
        String responseQuantityType = this.getResponseQuantityType();
        if (depth > thresholdDepth) {
            return new Response(asset.getID(), asset.getAssetClass(), hazardQuantityType,
                    responseQuantityType, 1.0); // Functional
        } else {
            return new Response(asset.getID(), asset.getAssetClass(), hazardQuantityType,
                    responseQuantityType, 0.0); // Non-functional
        }
    }

}
