package gov.lanl.nisac.fragility.exposure;

import gov.lanl.nisac.fragility.assets.IAsset;
import gov.lanl.nisac.fragility.gis.IFeatureCollection;
import gov.lanl.nisac.fragility.hazards.IHazardField;

public interface IExposure {
    IAsset getAsset();

    IHazardField getHazardField();
    
    IFeatureCollection getExposure();
}
