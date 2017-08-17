package gov.lanl.nisac.fragility.io;



public class RasterFieldData {
	
	private String uri;
	private String gridFormat;
	private String crsCode = "EPSG:4326";
	private int nBands;
	private int rasterBand;
	private Class<? extends Number> valueType;
	
	public RasterFieldData() {}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getGridFormat() {
		return gridFormat;
	}

	public void setGridFormat(String gridFormat) {
		this.gridFormat = gridFormat;
	}

	public String getCrsCode() {
		return crsCode;
	}

	public void setCrsCode(String crsCode) {
		this.crsCode = crsCode;
	}

	public int getnBands() {
		return nBands;
	}

	public void setnBands(int nBands) {
		this.nBands = nBands;
	}

	public int getRasterBand() {
		return rasterBand;
	}

	public void setRasterBand(int rasterBand) {
		this.rasterBand = rasterBand;
	}

	public Class<? extends Number> getValueType() {
		return valueType;
	}

	public void setValueType(Class<? extends Number> valueType) {
		this.valueType = valueType;
	}


}
