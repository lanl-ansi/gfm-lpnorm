package gov.lanl.nisac.fragility.core;

public class PropertyDescriptor implements IPropertyDescriptor {
	
	private String name;
	private Class<?> type;

	public PropertyDescriptor(String name, Class<?> type) {
		this.name = name;
		this.type = type;
	}


	@Override
	public String getName() {
		return name;
	}


	@Override
	public Class<?> getType() {
		return type;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyDescriptor other = (PropertyDescriptor) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.getSimpleName().equals(other.type.getSimpleName()))
			return false;
		return true;
	}

	

}
