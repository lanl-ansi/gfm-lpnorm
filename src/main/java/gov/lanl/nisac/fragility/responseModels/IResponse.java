package gov.lanl.nisac.fragility.responseModels;


public interface IResponse {

	String getAssetID();
	
	String getAssetClass();
	
	String getHazardQuantityType();
	
	String getResponseQuantityType();
	
	double getValue();
}
