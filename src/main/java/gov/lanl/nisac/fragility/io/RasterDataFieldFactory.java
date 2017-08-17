package gov.lanl.nisac.fragility.io;

import gov.lanl.nisac.fragility.gis.RasterField;
import gov.lanl.nisac.fragility.gis.RasterFieldFactory;

import java.io.File;
import java.net.URI;

/**
 * Decorator for RasterFieldFactory to make use of RasterFieldData instances directly.
 */
public class RasterDataFieldFactory extends RasterFieldFactory{

	public static RasterField createRasterField(RasterFieldData rasterFieldData){
		RasterField rasterField;

		URI uri = URI.create(rasterFieldData.getUri());
		File input = new File(uri);

		String format = rasterFieldData.getGridFormat();
		RasterFieldFormat fieldFormat = null;
		switch(format){
		case "GeoTiff":
			fieldFormat = RasterFieldFormat.GeoTiff;
			break;
		case "ArcGrid":
			fieldFormat = RasterFieldFormat.ArcGrid;
		}
		rasterField = createRasterField(input, fieldFormat, rasterFieldData.getnBands(),
				rasterFieldData.getRasterBand(),rasterFieldData.getValueType());

		return rasterField;
	}

}
