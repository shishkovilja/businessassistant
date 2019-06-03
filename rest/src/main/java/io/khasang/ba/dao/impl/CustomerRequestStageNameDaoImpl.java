package io.khasang.ba.dao.impl;

import io.khasang.ba.dao.CustomerRequestStageNameDao;
import io.khasang.ba.entity.CustomerRequestStageName;

public class CustomerRequestStageNameDaoImpl extends BasicDaoImpl<CustomerRequestStageName>
        implements CustomerRequestStageNameDao {
    public CustomerRequestStageNameDaoImpl(Class<CustomerRequestStageName> entityClass) {
        super(entityClass);
    }
}