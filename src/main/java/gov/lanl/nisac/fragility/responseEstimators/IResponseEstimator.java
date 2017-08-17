package gov.lanl.nisac.fragility.responseEstimators;

import gov.lanl.nisac.fragility.assets.IAsset;
import gov.lanl.nisac.fragility.core.IIdentifiable;
import gov.lanl.nisac.fragility.hazards.IHazardField;
import gov.lanl.nisac.fragility.responseModels.IResponse;

import java.util.Collection;
import java.util.List;

/**
 * @author rsqrd, ambro
 *
 * Modified for simpler type safety.
 */
public interface IResponseEstimator extends IIdentifiable {
	
	/**
	 * @return the class that defines the estimator's properties.
	 */
	String getResponseEstimatorClass();
	/**
	 * @return the asset class associated with this response estimator
	 */
	//IAssetClass getAssetClass();
	String getAssetClass();
	
	/**
	 * @return the hazard quantity types that must be associated with a subset of the hazard fields set to getResponse(...)
	 */
	List<String> getHazardQuantityTypes();
	
	/**
	 * @param asset
	 * @param hazardFields
	 * @return the calculated Response
	 */
	IResponse getResponse(IAsset asset, Collection<IHazardField> hazardFields);

	/**
	 * @return
	 */
	String getResponseQuantityType();
	
}
