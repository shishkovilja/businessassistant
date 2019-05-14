package io.khasang.ba.dao.impl;

import io.khasang.ba.dao.CustomerRequestTypeDao;
import io.khasang.ba.entity.CustomerRequestType;

public class CustomerRequestTypeDaoImpl extends BasicDaoImpl<CustomerRequestType> implements CustomerRequestTypeDao {
    public CustomerRequestTypeDaoImpl(Class<CustomerRequestType> entityClass) {
        super(entityClass);
    }
}
