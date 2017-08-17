package gov.lanl.nisac.fragility.io;

import java.util.Map;

public class ResponseEstimatorData {
	
	
	private String id;
	private String responseEstimatorClass;
	private String assetClass;
	private String[] hazardQuantityTypes;
	private String responseQuantityType;
	private Map<String, Object> properties;
	
	public ResponseEstimatorData() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResponseEstimatorClass() {
		return responseEstimatorClass;
	}

	public void setResponseEstimatorClass(String type) {
		this.responseEstimatorClass = type;
	}

	public String getAssetClass() {
		return assetClass;
	}

	public void setAssetClass(String assetClass) {
		this.assetClass = assetClass;
	}

	public String[] getHazardQuantityTypes() {
		return hazardQuantityTypes;
	}

	public String getResponseQuantityType() {
		return responseQuantityType;
	}

	public void setResponseQuantityType(String responseClass) {
		this.responseQuantityType = responseClass;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
	
}
