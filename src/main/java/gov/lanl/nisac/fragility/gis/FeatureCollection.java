package gov.lanl.nisac.fragility.gis;

import java.util.ArrayList;
import java.util.List;

public class FeatureCollection implements IFeatureCollection {
	
	private List<Feature> features;

	public FeatureCollection() {
		features = new ArrayList<>();
	}

	public void addFeature(Feature feature){
		features.add(feature);
	}

	@Override
	public Feature getFeature(int index) {
		return features.get(index);
	}

}
