package gov.lanl.nisac.fragility.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractEntityClass implements IEntityClass {
	private final String id;
	private Map<String,IPropertyDescriptor> propertyDescriptors;

	public AbstractEntityClass(String id) {
		this.id = id;
		this.propertyDescriptors = new HashMap<>();
	}

	public AbstractEntityClass(String id, Collection<IPropertyDescriptor> propertyDescriptors) {
		this(id);
		for(IPropertyDescriptor d: propertyDescriptors){
			this.propertyDescriptors.put(d.getName(), d);
		}
	}

	public Collection<IPropertyDescriptor> getPropertyDescriptors(){
		return propertyDescriptors.values();
	}
	
	public Collection<String> getPropertyNames(){
		return propertyDescriptors.keySet();
	}
	
	public IPropertyDescriptor getPropertyDescriptor(String name){
		return propertyDescriptors.get(name);
	}
	
	public boolean isValidProperty(IProperty property){
		return isValidProperty(property.getName(),property.getValue());
	}

	public boolean isValidProperty(String name, Object value){
		if(!propertyDescriptors.keySet().contains(name)){
			return false;
		}
		Class<?> type = propertyDescriptors.get(name).getType();
        return type.isAssignableFrom(value.getClass());
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AbstractEntityClass))
			return false;
		AbstractEntityClass other = (AbstractEntityClass) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String getID() {
		return id;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}
