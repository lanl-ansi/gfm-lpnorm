package gov.lanl.nisac.fragility.gis;

import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;

public interface IField {
	
	Class<? extends Number> getValueType();
	
	Number getValue(double x, double y);
    
    Number getValue(Coordinate coordinate);

    List<Number> getValues(CoordinateSequence coordinates);

    List<Number> getValues(List<Coordinate> coordinates);

}
