package gov.lanl.nisac.fragility.io;


public class ResponseData {
	
	private String assetID;
	private String assetClass;
	private String hazardQuantityType;
	private String responseQuantityType;
	private double value;
	
	public ResponseData() {}

	public String getAssetID() {
		return assetID;
	}

	public void setAssetID(String assetID) {
		this.assetID = assetID;
	}

	public String getAssetClass() {
		return assetClass;
	}

	public void setAssetClass(String assetClass) {
		this.assetClass = assetClass;
	}

	public String getHazardQuantityType() {
		return hazardQuantityType;
	}

	public void setHazardQuantityType(String hazardQuantityType) {
		this.hazardQuantityType = hazardQuantityType;
	}

	public String getResponseQuantityType() {
		return responseQuantityType;
	}

	public void setResponseQuantityType(String responseQuantityType) {
		this.responseQuantityType = responseQuantityType;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
