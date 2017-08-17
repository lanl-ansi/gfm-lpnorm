package gov.lanl.nisac.fragility.exposure;

import com.vividsolutions.jts.geom.Geometry;

import gov.lanl.nisac.fragility.gis.IFeatureCollection;
import gov.lanl.nisac.fragility.gis.IField;

public interface IExposureEvaluator {
	
	IFeatureCollection evaluateExposure(Geometry geometry, IField field);
	
	Class<? extends Geometry> getGeometryType();

}
