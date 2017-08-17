package gov.lanl.nisac.fragility.core;

import java.util.HashMap;
import java.util.Map;

public class Properties implements IProperties {
	
	private Map<String,IProperty> properties;
	
	public Properties(){
		properties = new HashMap<>();
	}

    public void addProperty(IProperty property){
		properties.put(property.getName(),property);
	}

	@Override
	public IProperty getProperty(String name) {
		return properties.get(name);
	}

	@Override
	public Object getValue(String name) {
		return properties.get(name).getValue();
	}

}
