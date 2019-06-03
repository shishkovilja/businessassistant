package io.khasang.ba.dao.impl;

import io.khasang.ba.dao.CustomerRequestDao;
import io.khasang.ba.entity.CustomerRequest;

public class CustomerRequestDaoImpl extends BasicDaoImpl<CustomerRequest> implements CustomerRequestDao {
    public CustomerRequestDaoImpl(Class<CustomerRequest> entityClass) {
        super(entityClass);
    }
}