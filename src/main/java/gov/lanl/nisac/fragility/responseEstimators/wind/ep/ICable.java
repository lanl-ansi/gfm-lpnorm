package gov.lanl.nisac.fragility.responseEstimators.wind.ep;

public interface ICable {

    /**
     * @return the attachmentHeight (m)
     */
    double getAttachmentHeight();

    /**
     * @param span (m)
     * @param iceRadialThickness (m)
     * @return the ice Volume (m^3)
     */
    double iceVolume(double span, double iceRadialThickness);

    /**
     * @param span (m)
     * @return the mass (kg)
     */
    double mass(double span);

    /**
     * @param iceRadialThickness (m)
     * @param span (m)
     * @return total area of span (m^2)
     */
    double plusIceTotalArea(double iceRadialThickness, double span);

}