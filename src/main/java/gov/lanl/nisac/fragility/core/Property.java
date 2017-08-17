package gov.lanl.nisac.fragility.core;

public class Property implements IProperty, IPropertyDescriptor {
	
	private IPropertyDescriptor propertyDescriptor;
	private Object value;

	public Property(IPropertyDescriptor propertyDescriptor, Object value){
		this.propertyDescriptor = propertyDescriptor;
		this.value = value;
	}
	
	public Property(String name, Object value){
		Class<?> type = value.getClass();
		this.propertyDescriptor = new PropertyDescriptor(name, type);
		this.value = value;
	}

	@Override
	public String getName() {
		return propertyDescriptor.getName();
	}

	@Override
	public Class<?> getType() {
		return propertyDescriptor.getType();
	}

	@Override
	public Object getValue() {
		return value;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((propertyDescriptor == null) ? 0 : propertyDescriptor
						.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Property other = (Property) obj;
		if (propertyDescriptor == null) {
			if (other.propertyDescriptor != null)
				return false;
		} else if (!propertyDescriptor.equals(other.propertyDescriptor))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}


}
