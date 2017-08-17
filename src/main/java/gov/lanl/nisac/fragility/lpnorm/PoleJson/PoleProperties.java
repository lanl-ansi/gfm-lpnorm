package gov.lanl.nisac.fragility.lpnorm.PoleJson;

/**
 * Created by 301338 on 6/26/2017.
 */
public class PoleProperties {

    private float baseDiameter = 0.2222f;
    private float cableSpan = 90.0f;
    private float commAttachmentHeight = 4.7244f;
    private float commCableDiameter = 0.04f;
    private int commCableNumber = 2;
    private float commCableWireDensity = 2700f;
    private float height = 9.144f;
    private float meanPoleStrength = 38600000f;
    private float powerAttachmentHeight = 5.6388f;
    private float powerCableDiameter = 0.009474f;
    private int powerCableNumber = 2;
    private float powerCableWireDensity = 2700f;
    private String powerCircuitName;
    private float stdDevPoleStrength = 7700000;
    private float topDiameter = 0.153616f;
    private float woodDensity = 500f;
    private String lineId;


    public PoleProperties(){}




    public float getCableSpan() {
        return cableSpan;
    }

    public void setCableSpan(float cableSpan) {
        this.cableSpan = cableSpan;
    }

    public float getCommAttachmentHeight() {
        return commAttachmentHeight;
    }

    public void setCommAttachmentHeight(float commAttachmentHeight) {
        this.commAttachmentHeight = commAttachmentHeight;
    }

    public float getCommCableDiameter() {
        return commCableDiameter;
    }

    public void setCommCableDiameter(float commCableDiameter) {
        this.commCableDiameter = commCableDiameter;
    }

    public int getCommCableNumber() {
        return commCableNumber;
    }

    public void setCommCableNumber(int commCableNumber) {
        this.commCableNumber = commCableNumber;
    }

    public float getCommCableWireDensity() {
        return commCableWireDensity;
    }

    public void setCommCableWireDensity(float commCableWireDensity) {
        this.commCableWireDensity = commCableWireDensity;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getMeanPoleStrength() {
        return meanPoleStrength;
    }

    public void setMeanPoleStrength(float meanPoleStrength) {
        this.meanPoleStrength = meanPoleStrength;
    }

    public float getPowerAttachmentHeight() {
        return powerAttachmentHeight;
    }

    public void setPowerAttachmentHeight(float powerAttachmentHeight) {
        this.powerAttachmentHeight = powerAttachmentHeight;
    }

    public float getPowerCableDiameter() {
        return powerCableDiameter;
    }

    public void setPowerCableDiameter(float powerCableDiameter) {
        this.powerCableDiameter = powerCableDiameter;
    }

    public int getPowerCableNumber() {
        return powerCableNumber;
    }

    public void setPowerCableNumber(int powerCableNumber) {
        this.powerCableNumber = powerCableNumber;
    }

    public float getPowerCableWireDensity() {
        return powerCableWireDensity;
    }

    public void setPowerCableWireDensity(float powerCableWireDensity) {
        this.powerCableWireDensity = powerCableWireDensity;
    }

    public String getPowerCircuitName() {
        return powerCircuitName;
    }

    public void setPowerCircuitName(String powerCircuitName) {
        this.powerCircuitName = powerCircuitName;
    }

    public float getStdDevPoleStrength() {
        return stdDevPoleStrength;
    }

    public void setStdDevPoleStrength(float stdDevPoleStrength) {
        this.stdDevPoleStrength = stdDevPoleStrength;
    }

    public float getTopDiameter() {
        return topDiameter;
    }

    public void setTopDiameter(float topDiameter) {
        this.topDiameter = topDiameter;
    }

    public float getWoodDensity() {
        return woodDensity;
    }

    public void setWoodDensity(float woodDensity) {
        this.woodDensity = woodDensity;
    }

    public float getBaseDiameter() {
        return baseDiameter;
    }

    public void setBaseDiameter(float baseDiameter) {
        this.baseDiameter = baseDiameter;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }
}
