package gov.lanl.nisac.fragility.hazards;

import gov.lanl.nisac.fragility.core.IEntity;
import gov.lanl.nisac.fragility.gis.IField;

public interface IHazardField extends IEntity {
	
	IField getField();
	
	String getHazardQuantityType();
}
