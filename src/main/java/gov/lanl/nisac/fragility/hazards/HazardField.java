package gov.lanl.nisac.fragility.hazards;

import gov.lanl.nisac.fragility.core.AbstractEntity;
import gov.lanl.nisac.fragility.gis.IField;

public class HazardField extends AbstractEntity implements IHazardField {

    private final IField field;
    private final String hazardQuantityType;

    public HazardField(String id, String hazardQuantityType,
                       IField field) {
        super(id);
        this.hazardQuantityType = hazardQuantityType;
        this.field = field;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof HazardField))
            return false;
        HazardField other = (HazardField) obj;
        if (field == null) {
            if (other.field != null)
                return false;
        } else if (!field.equals(other.field))
            return false;
        if (hazardQuantityType == null) {
            if (other.hazardQuantityType != null)
                return false;
        } else if (!hazardQuantityType.equals(other.hazardQuantityType))
            return false;
        return true;
    }

    @Override
    public IField getField() {
        return field;
    }

    @Override
    public String getHazardQuantityType() {
        return hazardQuantityType;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((field == null) ? 0 : field.hashCode());
        result = prime * result + ((hazardQuantityType == null) ? 0 : hazardQuantityType.hashCode());
        return result;
    }

}
