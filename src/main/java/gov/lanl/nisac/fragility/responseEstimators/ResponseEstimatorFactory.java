package gov.lanl.nisac.fragility.responseEstimators;

import java.util.List;

import gov.lanl.nisac.fragility.core.IProperties;
import gov.lanl.nisac.fragility.responseEstimators.flood.ThresholdFloodResponseEstimator;
import gov.lanl.nisac.fragility.responseEstimators.icewind.ep.PowerPoleIceWindStressEstimator;
import gov.lanl.nisac.fragility.responseEstimators.wind.ep.PowerPoleWindStressEstimator;
import gov.lanl.nisac.fragility.responseEstimators.wme.LinearBlastResponseEstimator;

public class ResponseEstimatorFactory implements IResponseEstimatorFactory {

    @Override
    public IResponseEstimator createResponseEstimator(String id,
                                                      String responseEstimatorClass, String assetClass, List<String> hazardQuantityTypes,
                                                      String responseQuantityType, IProperties properties) {
        IResponseEstimator responseEstimator = null;

        if (responseEstimatorClass.equals("LinearBlastResponseEstimator")) {
            double sureSafe = (double) properties.getValue("sureSafe");
            double sureKill = (double) properties.getValue("sureKill");
            responseEstimator = new LinearBlastResponseEstimator(id, responseEstimatorClass, assetClass,
                    responseQuantityType, sureSafe, sureKill);
        }
        if (responseEstimatorClass.equals("ThresholdFloodResponseEstimator")) {
            double thresholdDepth = (double) properties.getValue("thresholdDepth");
            responseEstimator = new ThresholdFloodResponseEstimator(id,
                    responseEstimatorClass, assetClass, thresholdDepth);
        }
        if (responseEstimatorClass.equals("PowerPoleWindStressEstimator")) {
            responseEstimator = new PowerPoleWindStressEstimator(id);
        }
        if (responseEstimatorClass.equals("PowerPoleIceWindStressEstimator")) {
            responseEstimator = new PowerPoleIceWindStressEstimator(id);
        }

        return responseEstimator;
    }

}
