package gov.lanl.nisac.fragility.engine;

import gov.lanl.nisac.fragility.exposure.IExposure;
import gov.lanl.nisac.fragility.exposure.IExposureEvaluator;
import gov.lanl.nisac.fragility.io.AssetDataStore;
import gov.lanl.nisac.fragility.io.HazardFieldDataStore;

import java.util.List;

/**
 * Interface for an exposure engine. An exposure engine limits the calculation to asset exposure evaluation only.
 */
public interface IExposureEngine {

    /**
     * Evaluates exposure using a single exposure evaluator instance.
     *
     * @param assetDataStore       - The asset data store.
     * @param hazardFieldDataStore - The hazard field data store.
     * @param exposureEvaluator    - The exposure evaluator to be applied to compatible assets.
     * @return the list of asset exposure instances for compatible assets.
     */
    List<IExposure> execute(AssetDataStore assetDataStore,
                            HazardFieldDataStore hazardFieldDataStore,
                            IExposureEvaluator exposureEvaluator);
}
