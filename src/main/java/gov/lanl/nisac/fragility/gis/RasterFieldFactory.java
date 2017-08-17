package gov.lanl.nisac.fragility.gis;

import java.io.File;
import java.io.IOException;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.arcgrid.ArcGridReader;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.opengis.coverage.grid.GridCoverageReader;

public class RasterFieldFactory {

    public enum RasterFieldFormat {
        GeoTiff("GeoTiff"),
        ArcGrid("ArcGrid");

        private String s;

        RasterFieldFormat(String s) {

        }

        public String toString() {
            return s;
        }
    }

    protected static RasterField createRasterField(File input, RasterFieldFormat format,
                                                   int nBands, int rasterBand, Class<? extends Number> valueType) {
        RasterField field = null;
        GridCoverage2D coverage;
        GridCoverageReader reader = null;
        try {
            switch (format) {
                case GeoTiff:
                    reader = new GeoTiffReader(input);
                    break;
                case ArcGrid:
                    reader = new ArcGridReader(input);
                    break;
            }
            coverage = (GridCoverage2D) reader.read(null);
            if (coverage != null) {
                field = new RasterField(coverage, nBands, rasterBand, valueType);
            }
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }
        return field;
    }

}
