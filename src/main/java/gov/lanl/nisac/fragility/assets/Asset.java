package gov.lanl.nisac.fragility.assets;

import gov.lanl.nisac.fragility.core.AbstractEntity;
import gov.lanl.nisac.fragility.core.IProperties;

import com.vividsolutions.jts.geom.Geometry;

public class Asset extends AbstractEntity implements IAsset {
	
	private Geometry geometry;

	public Asset(String id, String assetClass, Geometry geometry, IProperties properties) {
		super(id, assetClass, properties);
		this.geometry = geometry;
	}

	@Override
	public String getAssetClass() {
		return getEntityClass();
	}

	@Override
	public Geometry getGeometry() {
		return geometry;
	}

}
