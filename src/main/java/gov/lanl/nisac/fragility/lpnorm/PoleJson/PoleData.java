package gov.lanl.nisac.fragility.lpnorm.PoleJson;

import java.util.List;

/**
 * Created by 301338 on 6/26/2017.
 */
public class PoleData {

    private List<PoleAssets> assets;
    private List<PoleHazardFields> hazardFields;
    private List<PoleResponseEstimators> responseEstimators;

    public PoleData(){}

    public List<PoleAssets> getAssets() {
        return assets;
    }

    public void setAssets(List<PoleAssets> assets) {
        this.assets = assets;
    }

    public List<PoleHazardFields> getHazardFields() {
        return hazardFields;
    }

    public void setHazardFields(List<PoleHazardFields> hazardFields) {
        this.hazardFields = hazardFields;
    }

    public List<PoleResponseEstimators> getResponseEstimators() {
        return responseEstimators;
    }

    public void setResponseEstimators(List<PoleResponseEstimators> responseEstimators) {
        this.responseEstimators = responseEstimators;
    }
}
