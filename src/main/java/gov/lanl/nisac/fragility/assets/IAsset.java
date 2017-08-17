package gov.lanl.nisac.fragility.assets;

import gov.lanl.nisac.fragility.core.IEntity;

import com.vividsolutions.jts.geom.Geometry;

public interface IAsset extends IEntity {

	String getAssetClass();

	Geometry getGeometry();

}
