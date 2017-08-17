package gov.lanl.nisac.fragility.io;

import gov.lanl.nisac.fragility.hazards.IHazardField;

import java.util.Collection;

public interface IHazardFieldDataStore {
	
	Collection<String> getHazardQuantityTypes();
	
	Collection<IHazardField> getHazardFields(String hazardQuantityType);
	
	IHazardField getHazardField(String hazardQuantityType, String id);
	
	int size();

}
