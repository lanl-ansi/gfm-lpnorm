package gov.lanl.nisac.fragility.responseEstimators.wind.ep;

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

public class PowerPoleWindStressEstimator extends AbstractResponseEstimator {

    public PowerPoleWindStressEstimator(String id) {
        super(id, "PowerPoleWindStressEstimator", "PowerDistributionPole",
                Arrays.asList(new String[]{"Windspeed"}), "DamageProbability");
    }

    @Override
    public IResponse getResponse(IAsset asset, Collection<IHazardField> hazardFields) {
        if (!getAssetClass().equals(asset.getAssetClass())) {
            throw new IllegalArgumentException(
                    String.format("Asset is not of Asset Class %s", getAssetClass()));
        }

        String hazardQuantityType = this.getHazardQuantityTypes().get(0);
        // throws IllegalArgumentException if is hazardQuantityType is not found
        IHazardField windSpeedHazardValue = getHazardField(hazardFields, hazardQuantityType);
        IField field = windSpeedHazardValue.getField();
        IExposureEvaluator exposureEvaluator = new PointExposureEvaluator();

        IFeatureCollection evaluateExposure = exposureEvaluator.evaluateExposure(asset.getGeometry(), field);
        Feature exposure = evaluateExposure.getFeature(0);
        IProperty exposureProperty = exposure.getProperty("exposure");
        final double windSpeedKnots = (Double) exposureProperty.getValue();
        final double windSpeedmps = 0.514444 * windSpeedKnots;

        IDistributionLine distLine = extractDistributionLine(asset);

        final PoleFailure poleFailure = new PoleFailure(distLine, distLine.getCableSpan());
        final double probFailure = poleFailure.cumulativeProbability(0.0, windSpeedmps);

        return new Response(asset.getID(), asset.getAssetClass(), hazardQuantityType,
                "DamageProbability", probFailure);
    }

    private double extractCableSpan(IAsset asset) {
        double cableSpan = getDoubleProp(asset, "cableSpan");
        return cableSpan;
    }

    private ICable extractCommCable(IAsset asset) {
        double attachmentHeight = getDoubleProp(asset, "commAttachmentHeight");
        double diameter = getDoubleProp(asset, "commCableDiameter");
        double wireDensity = getDoubleProp(asset, "commCableWireDensity");
        int number = getIntProp(asset, "commCableNumber");
        return new Cable(diameter, wireDensity, attachmentHeight, number);
    }

    private IDistributionLine extractDistributionLine(final IAsset asset) {
        return new IDistributionLine() {
            @Override
            public double getCableSpan() {
                return extractCableSpan(asset);
            }

            @Override
            public ICable getCommCable() {
                return extractCommCable(asset);
            }

            @Override
            public IPole getPole() {
                return extractPole(asset);
            }

            @Override
            public ICable getPowerCable() {
                return extractPowerCable(asset);
            }
        };
    }

    private IPole extractPole(IAsset asset) {
        double height = getDoubleProp(asset, "height");
        double woodDensity = getDoubleProp(asset, "woodDensity");
        double meanPoleStrength = getDoubleProp(asset, "meanPoleStrength");
        double baseDiameter = getDoubleProp(asset, "baseDiameter");
        double stdDevPoleStrength = getDoubleProp(asset, "stdDevPoleStrength");
        double topDiameter = getDoubleProp(asset, "topDiameter");
        return new Pole(baseDiameter, topDiameter, height, woodDensity, meanPoleStrength, stdDevPoleStrength);
    }

    private ICable extractPowerCable(IAsset asset) {
        double attachmentHeight = getDoubleProp(asset, "powerAttachmentHeight");
        double diameter = getDoubleProp(asset, "powerCableDiameter");
        double wireDensity = getDoubleProp(asset, "powerCableWireDensity");
        int number = getIntProp(asset, "powerCableNumber");
        return new Cable(diameter, wireDensity, attachmentHeight, number);
    }

    private double getDoubleProp(IAsset asset, final String propKey) {
        IProperty prop = asset.getProperty(propKey);
        double value = ((Number) prop.getValue()).doubleValue();
        return value;
    }

    private int getIntProp(IAsset asset, final String propKey) {
        IProperty prop = asset.getProperty(propKey);
        int value = ((Number) prop.getValue()).intValue();
        return value;
    }

    @Override
    public String getID() {
        return "PowerPoleWindStressEstimator";
    }

}
