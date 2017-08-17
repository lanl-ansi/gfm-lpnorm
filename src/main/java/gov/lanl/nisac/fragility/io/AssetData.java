package gov.lanl.nisac.fragility.io;

import gov.lanl.nisac.fragility.assets.Asset;
import gov.lanl.nisac.fragility.assets.IAsset;
import gov.lanl.nisac.fragility.core.Properties;
import gov.lanl.nisac.fragility.core.Property;
import gov.lanl.nisac.fragility.io.utils.GeoJSONUtils;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vividsolutions.jts.geom.Geometry;

public class AssetData {
	private String id;
	private String assetClass;
	private JsonNode assetGeometry;
	private Map<String, Object> properties;
	
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public AssetData() {}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getAssetClass() {
		return assetClass;
	}

	public void setAssetClass(String assetClass) {
		this.assetClass = assetClass;
	}

	public void setAssetGeometry(JsonNode jsonGeom) {
		this.assetGeometry = jsonGeom;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
	
	public IAsset createAsset(){
		Geometry geom = GeoJSONUtils.GeoJSON2Geometry(assetGeometry.toString());
		Properties assetProperties = new Properties();

		for(String k: properties.keySet()){
			assetProperties.addProperty(new Property(k, properties.get(k)));
		}
		return new Asset(id, assetClass, geom, assetProperties);
	}

}