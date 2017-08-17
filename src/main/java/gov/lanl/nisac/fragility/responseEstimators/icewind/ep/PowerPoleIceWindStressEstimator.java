package gov.lanl.nisac.fragility.responseEstimators.icewind.ep;

import gov.lanl.nisac.fragility.assets.IAsset;
import gov.lanl.nisac.fragility.core.IProperty;
import gov.lanl.nisac.fragility.exposure.IExposureEvaluator;
import gov.lanl.nisac.fragility.exposure.PointExposureEvaluator;
import gov.lanl.nisac.fragility.gis.Feature;
import gov.lanl.nisac.fragility.gis.IFeatureCollection;
import gov.lanl.nisac.fragility.gis.IField;
import gov.lanl.nisac.fragility.hazards.IHazardField;
import gov.lanl.nisac.fragility.responseEstimators.AbstractResponseEstimator;
import gov.lanl.nisac.fragility.responseEstimators.wind.ep.Cable;
import gov.lanl.nisac.fragility.responseEstimators.wind.ep.ICable;
import gov.lanl.nisac.fragility.responseEstimators.wind.ep.IDistributionLine;
import gov.lanl.nisac.fragility.responseEstimators.wind.ep.IPole;
import gov.lanl.nisac.fragility.responseEstimators.wind.ep.Pole;
import gov.lanl.nisac.fragility.responseEstimators.wind.ep.PoleFailure;
import gov.lanl.nisac.fragility.responseModels.IResponse;
import gov.lanl.nisac.fragility.responseModels.Response;

import java.util.Arrays;
import java.util.Collection;

/**
 * Estimates the probability of failure given ice thickness in milimeters and windspeed in knots.
 */
public class PowerPoleIceWindStressEstimator extends AbstractResponseEstimator {

    public PowerPoleIceWindStressEstimator(String id) {
        super(id, "PowerPoleIceWindStressEstimator", "PowerDistributionPole",
                Arrays.asList("IceThickness", "Windspeed"), "DamageProbability");
    }

    @Override
    public IResponse getResponse(IAsset asset, Collection<IHazardField> hazardFields) {
        if (!getAssetClass().equals(asset.getAssetClass())) {
            throw new IllegalArgumentException(
                    String.format("Asset is not of Asset Class %s", getAssetClass()));
        }

        // IceThickness is the first hazard quantity type, Windspeed is the second.
        String iceHazardQuantity = this.getHazardQuantityTypes().get(0);
        String windHazardQuantity = this.getHazardQuantityTypes().get(1);

        // Evaluate exposures to ice and wind.
        // throws IllegalArgumentException if is hazardQuantityType is not found
        IHazardField iceHazardValue = getHazardField(hazardFields, iceHazardQuantity);
        IField iceField = iceHazardValue.getField();
        IExposureEvaluator exposureEvaluator = new PointExposureEvaluator();
        IFeatureCollection evaluateExposure = exposureEvaluator.evaluateExposure(asset.getGeometry(), iceField);
        Feature exposure = evaluateExposure.getFeature(0);
        IProperty exposureProperty = exposure.getProperty("exposure");
        final double iceThicknessMm = (Double) exposureProperty.getValue();
        final double iceThicknessMeters = iceThicknessMm * 1e-3;

        IHazardField windSpeedHazardValue = getHazardField(hazardFields, windHazardQuantity);
        IField windField = windSpeedHazardValue.getField();
        evaluateExposure = exposureEvaluator.evaluateExposure(asset.getGeometry(), windField);
        exposure = evaluateExposure.getFeature(0);
        exposureProperty = exposure.getProperty("exposure");
        final double windSpeedKnots = (Double) exposureProperty.getValue();
        final double windSpeedMps = 0.514444 * windSpeedKnots;

        IDistributionLine distLine = extractDistributionLine(asset);

        final PoleFailure poleFailure = new PoleFailure(distLine, distLine.getCableSpan());
        final double probFailure = poleFailure.cumulativeProbability(iceThicknessMeters, windSpeedMps);
        //System.out.println("probFailure: " + probFailure);

        return new Response(asset.getID(), asset.getAssetClass(), iceHazardQuantity + "-" + windHazardQuantity,
                "DamageProbability", probFailure);
    }

    private double extractCableSpan(IAsset asset) {
        return getDoubleProp(asset, "cableSpan");
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
        return ((Number) prop.getValue()).doubleValue();
    }

    private int getIntProp(IAsset asset, final String propKey) {
        IProperty prop = asset.getProperty(propKey);
        return ((Number) prop.getValue()).intValue();
    }

    @Override
    public String getID() {
        return "PowerPoleWindStressEstimator";
    }


}
