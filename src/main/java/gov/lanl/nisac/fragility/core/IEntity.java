
package gov.lanl.nisac.fragility.core;


public interface IEntity extends IIdentifiable {
	
	String getEntityClass();
	
	IProperties getProperties();
	
	IProperty getProperty(String name);
	
}
