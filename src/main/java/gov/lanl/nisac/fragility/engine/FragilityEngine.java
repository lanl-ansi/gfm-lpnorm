package gov.lanl.nisac.fragility.engine;

import gov.lanl.nisac.fragility.io.AssetDataStore;
import gov.lanl.nisac.fragility.io.HazardFieldDataStore;
import gov.lanl.nisac.fragility.io.ResponseEstimatorDataStore;
import gov.lanl.nisac.fragility.responseModels.IResponse;

import java.util.List;

public interface FragilityEngine {

	List<IResponse> execute(AssetDataStore assetDataStore,
							HazardFieldDataStore hazardFieldDataStore,
							ResponseEstimatorDataStore responseEstimatorDataStore);
}
