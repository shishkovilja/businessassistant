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
    public CustomerRequest addRequest(CustomerRequest customerRequest) {
        return customerRequestDao.add(customerRequest);
    }

    @Override
    public CustomerRequest getRequestById(long id) {
        return customerRequestDao.getById(id);
    }

    @Override
    public List<CustomerRequest> getAllRequests() {
        return customerRequestDao.getAll();
    }

    @Override
    public CustomerRequest updateRequest(CustomerRequest CustomerRequest) {
        return customerRequestDao.update(CustomerRequest);
    }

    @Override
    public CustomerRequest deleteRequest(long id) {
        return customerRequestDao.delete(getRequestById(id));
    }
}
