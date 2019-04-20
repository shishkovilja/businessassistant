package io.khasang.ba.service.impl;

import io.khasang.ba.dao.CustomerDao;
import io.khasang.ba.entity.Customer;
import io.khasang.ba.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of CustomerService based on DAO-layer utilization
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    /**
     * Add new Customer
     *
     * @param newCustomer New instance of Customer
     * @return Added {@link Customer} instance
     */
    @Override
    public Customer addCustomer(Customer newCustomer) {
        return customerDao.add(newCustomer);
    }

    /**
     * Get Customer by id
     *
     * @param id Identifier of the desired Customer
     * @return Found {@link Customer} instance
     */
    @Override
    public Customer getCustomerById(long id) {
        return customerDao.getById(id);
    }

    /**
     * Update existing Customer with new instance
     *
     * @param updatedCustomer Updated Customer instance
     * @return Updated {@link Customer} instance
     */
    @Override
    public Customer updateCustomer(Customer updatedCustomer) {
        return customerDao.update(updatedCustomer);
    }

    /**
     * Get all Customers
     *
     * @return {@link List} instance of all Customers
     */
    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.getAll();
    }

    /**
     * Delete Customer by id
     *
     * @param id Identifier of the Customer which should be deleted
     * @return Deleted {@link Customer} instance
     */
    @Override
    public Customer deleteCustomer(long id) {
        return customerDao.delete(getCustomerById(id));
    }
}
