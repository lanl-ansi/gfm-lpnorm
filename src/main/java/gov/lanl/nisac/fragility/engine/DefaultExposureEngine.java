package gov.lanl.nisac.fragility.engine;

import gov.lanl.nisac.fragility.assets.IAsset;
import gov.lanl.nisac.fragility.exposure.Exposure;
import gov.lanl.nisac.fragility.exposure.IExposure;
import gov.lanl.nisac.fragility.exposure.IExposureEvaluator;
import gov.lanl.nisac.fragility.gis.IFeatureCollection;
import gov.lanl.nisac.fragility.gis.IField;
import gov.lanl.nisac.fragility.hazards.IHazardField;
import gov.lanl.nisac.fragility.io.AssetDataStore;
import gov.lanl.nisac.fragility.io.HazardFieldDataStore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vividsolutions.jts.geom.Geometry;

/**
 * This default exposure engine presumes there is only one field of each hazard quantity type.
 */
public class DefaultExposureEngine implements IExposureEngine {

    /**
     * The default engine loops over hazard fields of a given quantity type, then applies the given
     * exposure evaluator to each asset with a compatible geometry.
     */
    @Override
    public List<IExposure> execute(AssetDataStore assetDataStore,
                                   HazardFieldDataStore hazardFieldDataStore,
                                   IExposureEvaluator evaluator) {

        List<IExposure> exposures = new ArrayList<>();
        Class<? extends Geometry> geometryType = evaluator.getGeometryType();
        Collection<String> hazardQuantityTypes = hazardFieldDataStore.getHazardQuantityTypes();

        for (String hazardQuantityType : hazardQuantityTypes) {
            List<IHazardField> hazardFields =
                    new ArrayList<>(hazardFieldDataStore.getHazardFields(hazardQuantityType));
            IHazardField hazardField = hazardFields.get(0);
            IField field = hazardField.getField();
            Collection<String> assetClasses = assetDataStore.getAssetClasses();

            for (String assetClass : assetClasses) {
                Collection<IAsset> assets = assetDataStore.getAssets(assetClass);

                for (IAsset asset : assets) {
                    Geometry geometry = asset.getGeometry();

                    if (geometryType.equals(geometry.getClass())) {
                        IFeatureCollection exposureFeatures = evaluator.evaluateExposure(geometry, field);
                        IExposure exposure = new Exposure(asset, hazardField, exposureFeatures);
                        exposures.add(exposure);
                    }
                }
            }
        }
        return exposures;
    }
}



