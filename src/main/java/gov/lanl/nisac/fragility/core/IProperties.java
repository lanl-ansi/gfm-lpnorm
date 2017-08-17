package gov.lanl.nisac.fragility.core;


public interface IProperties {

	IProperty getProperty(String name);
	
	Object getValue(String name);

}
