package gov.lanl.nisac.fragility.responseEstimators;

import java.util.List;

import gov.lanl.nisac.fragility.core.IProperties;

public interface IResponseEstimatorFactory {
	
	IResponseEstimator createResponseEstimator(String id,
											   String responseEstimatorClass, String assetClass, List<String> hazardQuantityTypes,
											   String responseQuantityType, IProperties properties);

}
