package gov.lanl.nisac.fragility.core;


public class AbstractEntity implements IEntity {

    protected final String id;
    protected IProperties properties = null;
    private String entityClass;

    public AbstractEntity(String id) {
        this.id = id;
    }

    public AbstractEntity(String id, String entityClass, IProperties properties) {
        this.id = id;
        this.entityClass = entityClass;
        this.properties = properties;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getEntityClass() {
        return entityClass;
    }

    @Override
    public IProperties getProperties() {
        return properties;
    }

    @Override
    public IProperty getProperty(String name) {
        return properties.getProperty(name);
    }
}
