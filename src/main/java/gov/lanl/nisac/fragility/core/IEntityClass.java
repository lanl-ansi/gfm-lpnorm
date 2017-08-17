package gov.lanl.nisac.fragility.core;

import java.util.Collection;

/**
 * Describes a class of entities and its known properties. The identifier of the entity class
 * is its class name.
 */
public interface IEntityClass extends IIdentifiable {
	
	IPropertyDescriptor getPropertyDescriptor(String name);

	boolean isValidProperty(String name, Object value);

	
}
