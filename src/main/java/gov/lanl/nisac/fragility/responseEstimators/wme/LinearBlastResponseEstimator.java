package gov.lanl.nisac.fragility.responseEstimators.wme;

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
import java.util.Collections;

public class LinearBlastResponseEstimator extends AbstractResponseEstimator {

    private final double sureSafe;
    private final double sureKill;

    public LinearBlastResponseEstimator(String id, String responseEstimatorClass, String assetClass,
                                        String responseQuantityType, double sureSafe, double sureKill) {
        super(id, responseEstimatorClass, assetClass,
                Collections.singletonList("BlastOverpressure"), responseQuantityType);
        this.sureSafe = sureSafe;
        this.sureKill = sureKill;
    }

    @Override
    public IResponse getResponse(IAsset asset, Collection<IHazardField> hazardFields)
            throws IllegalArgumentException {
        if (!getAssetClass().equals(asset.getAssetClass())) {
            throw new IllegalArgumentException(
                    String.format("Asset is not of Asset Class %s", getAssetClass()));
        }

        // throws IllegalArgumentException if is hazardField is not found
        String hazardQuantityType =
                this.getHazardQuantityTypes().get(0);
        String responseQuantityType = this.getResponseQuantityType();
        if (responseQuantityType.toString().equals("BlastOverpressure")) {
            // TODO do something ...
        }
        IHazardField blastOverpressureHazardValue = getHazardField(hazardFields, hazardQuantityType);
        IField field = blastOverpressureHazardValue.getField();
        IExposureEvaluator exposureEvaluator = new PointExposureEvaluator();

        IFeatureCollection evaluateExposure = exposureEvaluator.evaluateExposure(
                asset.getGeometry(), field);
        Feature exposure = evaluateExposure.getFeature(0);
        IProperty exposureProperty = exposure.getProperty("exposure");
        double overpressure = (Double) exposureProperty.getValue();
        if (overpressure > sureKill) {
            return new Response(asset.getID(), asset.getAssetClass(), hazardQuantityType,
                    "FailureProbability", 1.0);
        } else if (overpressure < sureSafe) {
            return new Response(asset.getID(), asset.getAssetClass(), hazardQuantityType,
                    "FailureProbability", 0.0);
        } else {
            double value = (overpressure - sureSafe) / (sureKill - sureSafe);
            return new Response(asset.getID(), asset.getAssetClass(), hazardQuantityType,
                    "FailureProbability", value);
        }
    }

}
