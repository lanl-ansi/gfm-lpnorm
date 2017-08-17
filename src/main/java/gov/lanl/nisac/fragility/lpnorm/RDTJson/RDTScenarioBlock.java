package gov.lanl.nisac.fragility.lpnorm.RDTJson;

import java.util.List;

/**
 * Created by 301338 on 6/30/2017.
 */
public class RDTScenarioBlock {

    private List<RDTScenarios> scenarios;

    public RDTScenarioBlock(){}

    public List<RDTScenarios> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<RDTScenarios> scenarios) {
        this.scenarios = scenarios;
    }
}
