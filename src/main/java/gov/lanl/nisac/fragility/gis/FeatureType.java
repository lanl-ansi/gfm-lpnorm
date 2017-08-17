package gov.lanl.nisac.fragility.gis;

import gov.lanl.nisac.fragility.core.AbstractEntityClass;
import gov.lanl.nisac.fragility.core.IPropertyDescriptor;

import java.util.Collection;

import com.vividsolutions.jts.geom.Geometry;

public class FeatureType extends AbstractEntityClass {

    private final Class<? extends Geometry> geometryType;
    private static final String crs = "EPSG:4326";

    public FeatureType(String name, Class<? extends Geometry> geometryType,
                       Collection<IPropertyDescriptor> propertyDescriptors) {
        super(name, propertyDescriptors);
        if (!Features.isAllowedType(geometryType)) {
            throw new RuntimeException("FeatureType: geometry type is not allowed.");
        }
        this.geometryType = geometryType;
    }

}
