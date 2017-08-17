package gov.lanl.nisac.fragility.lpnorm.PoleJson;

/**
 * Created by 301338 on 6/27/2017.
 */
public class PoleRasterFieldData {

    private String uri;
    private String gridFormat;
    private String crsCode;
    private int nBands;
    private int rasterBand;
    private String valueType;


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getGridFormat() {
        return gridFormat;
    }

    public void setGridFormat(String gridFormat) {
        this.gridFormat = gridFormat;
    }

    public String getCrsCode() {
        return crsCode;
    }

    public void setCrsCode(String crsCode) {
        this.crsCode = crsCode;
    }

    public int getnBands() {
        return nBands;
    }

    public void setnBands(int nBands) {
        this.nBands = nBands;
    }

    public int getRasterBand() {
        return rasterBand;
    }

    public void setRasterBand(int rasterBand) {
        this.rasterBand = rasterBand;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }
}
