package gov.lanl.nisac.fragility.core;

public interface IPropertyDescriptor {
	
	String getName();
	
	Class<?> getType();

	int hashCode();
	
	boolean equals(Object obj);

}
