package gov.lanl.nisac.fragility.responseModels;


public class Response implements IResponse {
	
	private final String assetID;
	private final String assetClass;
	private final String hazardQuantityType;
	private final String responseQuantityType;
	private final double value;


	public Response(String assetID, String assetClass,
			String hazardQuantityType, String responseQuantityType, double value) {
		this.assetID = assetID;
		this.assetClass = assetClass;
		this.hazardQuantityType = hazardQuantityType;
		this.responseQuantityType = responseQuantityType;
		this.value = value;
	}


	@Override
	public String getAssetID() {
		return assetID;
	}


	@Override
	public String getAssetClass() {
		return assetClass;
	}


	@Override
	public String getHazardQuantityType() {
		return hazardQuantityType;
	}


	@Override
	public String getResponseQuantityType() {
		return responseQuantityType;
	}


	@Override
	public double getValue() {
		return value;
	}

}
