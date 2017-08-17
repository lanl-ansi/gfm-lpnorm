package gov.lanl.nisac.fragility.lpnorm.PoleJson;

import java.util.List;

/**
 * Created by 301338 on 6/27/2017.
 */
public class PoleResponseEstimators {

    private String id;
    private String responseEstimatorClass;
    private String assetClass;
    private List<String> hazardQuantityTypes;
    private String responseQuantityType;
    private PoleResponseEstimatorsProperties properties;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResponseEstimatorClass() {
        return responseEstimatorClass;
    }

    public void setResponseEstimatorClass(String responseEstimatorClass) {
        this.responseEstimatorClass = responseEstimatorClass;
    }

    public List<String> getHazardQuantityTypes() {
        return hazardQuantityTypes;
    }

    public void setHazardQuantityTypes(List<String> hazardQuantityTypes) {
        this.hazardQuantityTypes = hazardQuantityTypes;
    }

    public String getResponseQuantityType() {
        return responseQuantityType;
    }

    public void setResponseQuantityType(String responseQuantityType) {
        this.responseQuantityType = responseQuantityType;
    }

    public String getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(String assetClass) {
        this.assetClass = assetClass;
    }

    public PoleResponseEstimatorsProperties getProperties() {
        return properties;
    }

    public void setProperties(PoleResponseEstimatorsProperties properties) {
        this.properties = properties;
    }
}
