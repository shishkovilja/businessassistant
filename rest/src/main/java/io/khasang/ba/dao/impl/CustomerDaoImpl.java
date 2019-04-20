package io.khasang.ba.dao.impl;

import io.khasang.ba.dao.CustomerDao;
import io.khasang.ba.entity.Customer;

public class CustomerDaoImpl extends BasicDaoImpl<Customer> implements CustomerDao {
    public CustomerDaoImpl(Class<Customer> entityClass) {
        super(entityClass);
    }
}
