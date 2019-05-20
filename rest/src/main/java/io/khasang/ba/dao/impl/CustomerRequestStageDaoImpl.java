package io.khasang.ba.dao.impl;

import io.khasang.ba.dao.CustomerRequestStageDao;
import io.khasang.ba.entity.CustomerRequestStage;

public class CustomerRequestStageDaoImpl extends BasicDaoImpl<CustomerRequestStage>
        implements CustomerRequestStageDao {
    public CustomerRequestStageDaoImpl(Class<CustomerRequestStage> entityClass) {
        super(entityClass);
    }
}