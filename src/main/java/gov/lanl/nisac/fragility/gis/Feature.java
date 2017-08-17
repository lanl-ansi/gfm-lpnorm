package gov.lanl.nisac.fragility.gis;

import gov.lanl.nisac.fragility.core.AbstractEntity;
import gov.lanl.nisac.fragility.core.IProperties;

import com.vividsolutions.jts.geom.Geometry;

public class Feature extends AbstractEntity {

	public Feature(String id, FeatureType featureType, IProperties properties) {
		super(id,featureType.getID(),properties);
	}
}
