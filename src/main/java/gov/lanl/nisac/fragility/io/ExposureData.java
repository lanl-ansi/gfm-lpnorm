package gov.lanl.nisac.fragility.io;


public class ExposureData {
	
	private String assetID;
	private String assetClass;
	private String hazardQuantityType;
	private double value;
	
	public ExposureData() {}

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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
