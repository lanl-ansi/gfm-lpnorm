package gov.lanl.nisac.fragility.io;

import gov.lanl.nisac.fragility.responseEstimators.IResponseEstimator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ResponseEstimatorDataStore  {
	
	protected Map<String,IResponseEstimator> responseEstimators;

	public ResponseEstimatorDataStore() {
		responseEstimators = new HashMap<>();
	}

	public void addResponseEstimator(IResponseEstimator responseEstimator){
		String responseEstimatorClass = responseEstimator.getResponseEstimatorClass();
		responseEstimators.put(responseEstimatorClass, responseEstimator);
	}

	public Collection<IResponseEstimator> getResponseEstimators() {
		return responseEstimators.values();
	}

	public int size() {
		return responseEstimators.size();
	}

}
