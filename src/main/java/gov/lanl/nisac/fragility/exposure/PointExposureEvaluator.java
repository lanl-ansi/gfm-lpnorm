package gov.lanl.nisac.fragility.exposure;

import gov.lanl.nisac.fragility.core.IPropertyDescriptor;
import gov.lanl.nisac.fragility.core.Properties;
import gov.lanl.nisac.fragility.core.Property;
import gov.lanl.nisac.fragility.core.PropertyDescriptor;
import gov.lanl.nisac.fragility.gis.Feature;
import gov.lanl.nisac.fragility.gis.FeatureCollection;
import gov.lanl.nisac.fragility.gis.FeatureType;
import gov.lanl.nisac.fragility.gis.IFeatureCollection;
import gov.lanl.nisac.fragility.gis.IField;

import java.util.ArrayList;
import java.util.Collection;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

/**
 * Simplest exposure evaluator. Evaluates exposure to a hazard field at a point.
 * Returns an Feature instance containing the given point, and the exposure value as the sole property.
 */
public class PointExposureEvaluator implements IExposureEvaluator {

    private static final FeatureType pointExposureType;
    private static final String featureID = "1";

    static {
        Collection<IPropertyDescriptor> descs = new ArrayList<>();
        PropertyDescriptor desc = new PropertyDescriptor("exposure", Double.class);
        descs.add(desc);
        pointExposureType = new FeatureType("POINT_EXPOSURE", Point.class, descs);
    }

    @Override
    public IFeatureCollection evaluateExposure(Geometry geometry, IField field) {
        // Check that the geometry is a point.
        String geomType = geometry.getGeometryType();

        if (!geomType.equals("Point")) {
            throw new RuntimeException("PointExposureEvaluator: " + geomType + "geometry argument. "
                    + "The geometry must be a Point.");
        }
        Point p = (Point) geometry;
        double exposure = field.getValue(p.getX(), p.getY()).doubleValue();
        Properties properties = new Properties();
        properties.addProperty(new Property(pointExposureType.getPropertyDescriptor("exposure"), exposure));
        Feature feature = new Feature(featureID, pointExposureType, properties);
        FeatureCollection collection = new FeatureCollection();
        collection.addFeature(feature);

        return collection;
    }

    @Override
    public Class<? extends Geometry> getGeometryType() {
        return Point.class;
    }


}
