package io.khasang.ba.dao.impl;

import io.khasang.ba.dao.AddressDao;
import io.khasang.ba.entity.Address;

public class AddressDaoImpl extends BasicDaoImpl<Address> implements AddressDao {
    public AddressDaoImpl(Class<Address> entityClass) {
        super(entityClass);
    }
}
