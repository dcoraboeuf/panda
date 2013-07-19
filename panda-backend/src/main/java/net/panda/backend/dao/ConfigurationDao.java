package net.panda.backend.dao;

public interface ConfigurationDao {
    String getValue(String name);

    void setValue(String name, String value);
}
