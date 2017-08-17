package gov.lanl.nisac.fragility.io;

public class HazardFieldData {
	
	private RasterFieldData rasterFieldData;
	private String id;
	private String hazardQuantityType;
	
	public HazardFieldData() {}

	public RasterFieldData getRasterFieldData() {
		return rasterFieldData;
	}

	public void setRasterFieldData(RasterFieldData rasterFieldData) {
		this.rasterFieldData = rasterFieldData;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHazardQuantityType() {
		return hazardQuantityType;
	}

	public void setHazardQuantityType(String hazardQuantityType) {
		this.hazardQuantityType = hazardQuantityType;
	}
	
}
