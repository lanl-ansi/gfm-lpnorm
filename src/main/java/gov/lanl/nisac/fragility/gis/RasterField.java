package gov.lanl.nisac.fragility.gis;

import java.util.ArrayList;
import java.util.List;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridGeometry2D;
import org.geotools.geometry.Envelope2D;
import org.geotools.geometry.jts.JTS;
import org.opengis.coverage.grid.GridEnvelope;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;

public class RasterField implements IField {

    private GridCoverage2D gridCoverage = null;
    private CoordinateReferenceSystem crs = null;
    private int nBands;
    private int rasterBand;
    private Class<? extends Number> valueType;
    private int[] gridSize;
    private double[] lowerLeftCorner;
    private double[] upperRightCorner;
    private double[] cellSize;
    private Envelope2D envelope;
    private GridEnvelope gridEnvelope;
    private Number noDataValue = new Double(0);

    public RasterField(GridCoverage2D gridCoverage, int nBands, int rasterBand, Class<? extends Number> valueType) {
        this.gridCoverage = gridCoverage;
        this.crs = gridCoverage.getCoordinateReferenceSystem();
        this.nBands = nBands;
        this.rasterBand = rasterBand;
        this.valueType = valueType;
        GridGeometry2D gridGeom = gridCoverage.getGridGeometry();
        envelope = new Envelope2D(gridGeom.getEnvelope());
        gridEnvelope = gridGeom.getGridRange();
        gridSize = new int[2];
        gridSize[0] = gridEnvelope.getHigh(0) + 1;
        gridSize[1] = gridEnvelope.getHigh(1) + 1;
        lowerLeftCorner = new double[2];
        lowerLeftCorner[0] = envelope.getMinimum(0);
        lowerLeftCorner[1] = envelope.getMinimum(1);
        upperRightCorner = new double[2];
        upperRightCorner[0] = envelope.getMaximum(0);
        upperRightCorner[1] = envelope.getMaximum(1);
        cellSize = new double[2];
        cellSize[0] = (upperRightCorner[0] - lowerLeftCorner[0]) / gridSize[0];
        cellSize[1] = (upperRightCorner[1] - lowerLeftCorner[1]) / gridSize[1];
    }

    @Override
    public Number getValue(double x, double y) {
        return getValue(new Coordinate(x, y));
    }

    @Override
    public Number getValue(Coordinate coordinate) {
        DirectPosition p = JTS.toDirectPosition(coordinate, crs);
        if (!isStrictlyWithin(p, envelope)) {
            return noDataValue;
        }
        String t = valueType.getSimpleName();
        Number result = null;

        if (t.toLowerCase().equals("double")) {
            double[] r = gridCoverage.evaluate(p, new double[nBands]);
            result = new Double(r[rasterBand - 1]);
        } else if (t.toLowerCase().equals("float")) {
            float[] r = gridCoverage.evaluate(p, new float[nBands]);
            result = new Float(r[rasterBand - 1]);
        } else if (t.toLowerCase().equals("integer")) {
            int[] r = gridCoverage.evaluate(p, new int[nBands]);
            result = new Integer(r[rasterBand - 1]);
        }
        return result;
    }

    @Override
    public List<Number> getValues(CoordinateSequence coordinates) {
        int n = coordinates.size();
        if (n != 0) {
            List<Number> list = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                list.add(this.getValue(coordinates.getCoordinate(i)));
            }

            return list;
        } else return null;
    }

    @Override
    public List<Number> getValues(List<Coordinate> coordinates) {
        int n = coordinates.size();
        if (n != 0) {
            List<Number> list = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                list.add(this.getValue(coordinates.get(i)));
            }

            return list;

        } else return null;
    }

    @Override
    public Class<? extends Number> getValueType() {
        return valueType;
    }

    public int[] getGridSize() {
        return gridSize;
    }

    public double[] getCellSize() {
        return cellSize;
    }

    public double[] getLowerLeftCorner() {
        return lowerLeftCorner;
    }

    public double[] getUpperRightCorner() {
        return upperRightCorner;
    }

    public CoordinateReferenceSystem getCrs() {
        return crs;
    }

    public int getnBands() {
        return nBands;
    }

    public int getRasterBand() {
        return rasterBand;
    }

    /**
     * Custom check on envelope containment to avoid exceptions.
     *
     * @param p
     * @param e
     * @return
     */
    private boolean isStrictlyWithin(DirectPosition p, Envelope2D e) {
        double[] c = p.getCoordinate();
        double x = c[0];
        double y = c[1];
        double xmin = e.getMinX();
        double xmax = e.getMaxX();
        double ymin = e.getMinY();
        double ymax = e.getMaxY();
        return (x > xmin) && (x < xmax) &&
                (y > ymin) && (y < ymax);
    }

}
