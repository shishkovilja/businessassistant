package io.khasang.ba.service.impl;

import io.khasang.ba.dao.AddressDao;
import io.khasang.ba.entity.Address;
import io.khasang.ba.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressDao addressDao;

    @Override
    public Address addAddress(Address address) {
        return addressDao.add(address);
    }

    @Override
    public Address getAddressById(long id) {
        return addressDao.getById(id);
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressDao.getAll();
    }

    @Override
    public Address updateAddress(Address address) {
        return addressDao.update(address);
    }

    @Override
    public Address deleteAddress(long id) {
        return addressDao.delete(this.getAddressById(id));
    }
}
