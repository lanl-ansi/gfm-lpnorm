package gov.lanl.nisac.fragility.responseEstimators;

import gov.lanl.nisac.fragility.hazards.IHazardField;

import java.util.Collection;
import java.util.List;

public abstract class AbstractResponseEstimator implements IResponseEstimator {

    private final String id;
    private final String responseEstimatorClass;
    private final String assetClass;
    private List<String> hazardQuantityTypes;
    private final String responseQuantityType;

    public AbstractResponseEstimator(String id, String responseEstimatorClass, String assetClass,
                                     List<String> hazardQuantityTypes, String responseQuantityType) {
        this.id = id;
        this.assetClass = assetClass;
        this.responseEstimatorClass = responseEstimatorClass;
        this.hazardQuantityTypes = hazardQuantityTypes;
        this.responseQuantityType = responseQuantityType;
    }

    @Override
    public String getResponseEstimatorClass() {
        return responseEstimatorClass;
    }

    @Override
    public String getAssetClass() {
        return assetClass;
    }

    /* (non-Javadoc)
     * @see gov.lanl.nisac.fragility.core.IIdentifiable#getID()
     */
    @Override
    public String getID() {
        return id;
    }

    /* (non-Javadoc)
     * @see gov.lanl.nisac.fragility.responseEstimators.IResponseEstimator#getRequiredHazardQuantityTypes()
     */
    @Override
    public List<String> getHazardQuantityTypes() {
        return hazardQuantityTypes;
    }

    /* (non-Javadoc)
     * @see gov.lanl.nisac.fragility.responseEstimators.IResponseEstimator#getResponseClass()
     */
    @Override
    public String getResponseQuantityType() {
        return responseQuantityType;
    }

    /**
     * @param hazardFields
     * @param hazardQuantityType
     * @return
     * @throws IllegalArgumentException if is hazardQuantityType is not found
     */
    protected IHazardField getHazardField(Collection<IHazardField> hazardFields, String hazardQuantityType)
            throws IllegalArgumentException {
        IHazardField hazardField = null;
        for (IHazardField hv : hazardFields) {
            String hq = hv.getHazardQuantityType();
            if (hazardQuantityType.equals(hq)) {
                hazardField = hv;
                break;
            }
        }
        if (hazardField == null) {
            throw new IllegalArgumentException("Exposing Field " + hazardQuantityType.toString() + " was not found.");
        }
        return hazardField;
    }

}
