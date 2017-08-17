package gov.lanl.nisac.fragility.io;

import gov.lanl.nisac.fragility.hazards.IHazardField;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HazardFieldDataStore implements IHazardFieldDataStore {

    protected Map<String, Map<String, IHazardField>> hazardFields;

    public HazardFieldDataStore() {
        hazardFields = new HashMap<>();
    }

    public void addHazardField(IHazardField hazardField) {
        String hazardQuantityType = hazardField.getHazardQuantityType();
        Map<String, IHazardField> map = hazardFields.get(hazardQuantityType);

        if (map == null) {
            map = new HashMap<>();
        }

        map.put(hazardField.getID(), hazardField);
        hazardFields.put(hazardQuantityType, map);
    }

    @Override
    public Collection<String> getHazardQuantityTypes() {
        return hazardFields.keySet();
    }


    @Override
    public Collection<IHazardField> getHazardFields(
            String hazardQuantityType) {
        return hazardFields.get(hazardQuantityType).values();
    }

    @Override
    public IHazardField getHazardField(String hazardQuantityType,
                                       String id) {
        Map<String, IHazardField> map = hazardFields.get(hazardQuantityType);
        if (map != null) {
            return map.get(id);
        } else {
            return null;
        }
    }

    @Override
    public int size() {
        int n = 0;
        for (Map<String, IHazardField> map : hazardFields.values()) {
            n += map.size();
        }
        return n;
    }

}
