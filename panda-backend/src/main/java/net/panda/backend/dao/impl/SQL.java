package net.panda.backend.dao.impl;

public interface SQL {
    String PIPELINE_ALL = "SELECT * FROM PIPELINE ORDER BY NAME";
    String PIPELINE_BY_ID = "SELECT * FROM PIPELINE WHERE ID = :id";
}
