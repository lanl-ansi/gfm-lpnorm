package gov.lanl.nisac.fragility.io;

import gov.lanl.nisac.fragility.assets.IAsset;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AssetDataStore {
	
	protected Map<String,Map<String,IAsset>> assets;

	public AssetDataStore() {
		assets = new HashMap<>();
	}
	
	public void addAsset(IAsset asset){
		String assetClass = asset.getAssetClass();
		Map<String,IAsset> map = assets.get(assetClass);

		if(map==null){
			map = new HashMap<>();
		}

		map.put(asset.getID(),asset);
		assets.put(assetClass, map);
	}


	public Collection<String> getAssetClasses() {
		return assets.keySet();
	}


	public Collection<IAsset> getAssets(String assetClass) {
		return assets.get(assetClass).values();
	}

	public int size() {
		int n = 0;
		for(Map<String,IAsset> map: assets.values()){
			n+= map.size();
		}
		return n;
	}



}
