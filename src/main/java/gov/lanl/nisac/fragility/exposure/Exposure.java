package gov.lanl.nisac.fragility.exposure;

import gov.lanl.nisac.fragility.assets.IAsset;
import gov.lanl.nisac.fragility.gis.IFeatureCollection;
import gov.lanl.nisac.fragility.gis.IField;
import gov.lanl.nisac.fragility.hazards.IHazardField;


public class Exposure implements IExposure {
	
	private final IAsset asset;
	private final IHazardField field;
	private final IFeatureCollection exposure;
	
	public Exposure(IAsset asset, IHazardField field, IFeatureCollection exposure) {
		this.asset = asset;
		this.field = field;
		this.exposure = exposure;
	}

	@Override
	public IAsset getAsset() {
		return asset;
	}

	@Override
	public IHazardField getHazardField() {
		return field;
	}

	@Override
	public IFeatureCollection getExposure() {
		return exposure;
	}

}
