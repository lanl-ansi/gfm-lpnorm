package gov.lanl.nisac.fragility.lpnorm.PoleJson;

/**
 * Created by Trevor Crawford on 6/26/2017.
 */
public class PoleAssets {

    private String assetClass;
    private PoleAssetGeometry assetGeometry;
    private String id;
    private PoleProperties properties;

    public PoleAssets(){}

    public void setAssetClass(String ac){
        this.assetClass = ac;
    }

    public String getAssetClass(){
        return this.assetClass;
    }

    public PoleProperties getProperties() {
        return properties;
    }

    public void setProperties(PoleProperties properties) {
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PoleAssetGeometry getAssetGeometry() {
        return assetGeometry;
    }

    public void setAssetGeometry(PoleAssetGeometry assetGeometry) {
        this.assetGeometry = assetGeometry;
    }
}
