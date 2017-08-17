package gov.lanl.nisac.fragility.lpnorm.PoleJson;

/**
 * Created by 301338 on 6/26/2017.
 */
public class PoleAssetGeometry {

    private double coordinates[];
    private String type;

    public PoleAssetGeometry(){}

    public void setType(String t){
        this.type = t;
    }

    public String getType(){
        return this.type;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }
}
