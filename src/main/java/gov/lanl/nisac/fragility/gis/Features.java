package gov.lanl.nisac.fragility.gis;

import java.util.Arrays;
import java.util.List;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class Features {
    private static final Class<?>[] allowedGeometries = {Point.class, LineString.class,
            Polygon.class, MultiPoint.class, MultiLineString.class, MultiPolygon.class};
    private static final List<Class<?>> allowed;

    static {
        allowed = Arrays.asList(allowedGeometries);
    }

    private Features() {
    }

    public static boolean isAllowedType(Class<? extends Geometry> type) {
        return allowed.contains(type);
    }
}
