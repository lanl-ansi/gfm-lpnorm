package gov.lanl.nisac.fragility.io.utils;

import java.io.IOException;
import java.io.StringReader;

import org.geotools.geojson.geom.GeometryJSON;

import com.vividsolutions.jts.geom.Geometry;

public class GeoJSONUtils {
	
	private static final GeometryJSON geomJSON = new GeometryJSON();

	public static Geometry GeoJSON2Geometry(String jsonGeometry){
		Geometry geometry = null;
		StringReader reader = new StringReader(jsonGeometry);
		try {
			geometry = geomJSON.read(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return geometry;
	}

}
