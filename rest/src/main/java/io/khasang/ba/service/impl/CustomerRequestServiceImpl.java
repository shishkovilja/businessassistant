package io.khasang.ba.service.impl;

import io.khasang.ba.dao.CustomerRequestDao;
import io.khasang.ba.entity.CustomerRequest;
import io.khasang.ba.service.CustomerRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerRequestServiceImpl implements CustomerRequestService {

    @Autowired
    private CustomerRequestDao customerRequestDao;

    @Override
    public CustomerRequest addCustomerRequest(CustomerRequest customerRequest) {
        return customerRequestDao.add(customerRequest);
    }

    @Override
    public CustomerRequest getCustomerRequestById(long id) {
        return customerRequestDao.getById(id);
    }

    @Override
    public List<CustomerRequest> getAllCustomerRequests() {
        return customerRequestDao.getAll();
    }

    @Override
    public CustomerRequest updateCustomerRequest(CustomerRequest CustomerRequest) {
        return customerRequestDao.update(CustomerRequest);
    }

    @Override
    public CustomerRequest deleteCustomerRequest(long id) {
        return customerRequestDao.delete(getCustomerRequestById(id));
    }
}
