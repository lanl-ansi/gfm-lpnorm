package gov.lanl.nisac.fragility.engine;

import gov.lanl.nisac.fragility.assets.IAsset;
import gov.lanl.nisac.fragility.hazards.IHazardField;
import gov.lanl.nisac.fragility.io.AssetDataStore;
import gov.lanl.nisac.fragility.io.HazardFieldDataStore;
import gov.lanl.nisac.fragility.io.ResponseEstimatorDataStore;
import gov.lanl.nisac.fragility.responseEstimators.IResponseEstimator;
import gov.lanl.nisac.fragility.responseModels.IResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This default engine presumes there is only one field of each hazard quantity type.
 */
public class DefaultFragilityEngine implements FragilityEngine {


    @Override
    public List<IResponse> execute(AssetDataStore assetDataStore,
                                   HazardFieldDataStore hazardFieldDataStore,
                                   ResponseEstimatorDataStore responseEstimatorDataStore) {
        Collection<IResponseEstimator> responseEstimators = responseEstimatorDataStore.getResponseEstimators();
        List<IResponse> responses = new ArrayList<>();

        for (IResponseEstimator estimator : responseEstimators) {
            List<String> hazardQuantityTypes = estimator.getHazardQuantityTypes();
            List<IHazardField> hazardFields = new ArrayList<>();

            for (String hazardQuantityType : hazardQuantityTypes) {
                List<IHazardField> hazardQuantityFields = new ArrayList<>(hazardFieldDataStore.getHazardFields(hazardQuantityType));
                hazardFields.add(hazardQuantityFields.get(0));
            }

            String assetClass = estimator.getAssetClass();
            Collection<IAsset> assets = assetDataStore.getAssets(assetClass);

            for (IAsset asset : assets) {
                IResponse response = estimator.getResponse(asset, hazardFields);
                responses.add(response);
            }
        }
        return responses;
    }

}
