package io.khasang.ba.service.impl;

import io.khasang.ba.dao.CustomerRequestTypeDao;
import io.khasang.ba.entity.CustomerRequestType;
import io.khasang.ba.service.CustomerRequestTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of CustomerRequestTypeService based on DAO-layer utilization
 */
@Service
public class CustomerRequestTypeServiceImpl implements CustomerRequestTypeService {

    @Autowired
    private CustomerRequestTypeDao customerRequestTypeDao;

    /**
     * Add new customer request type
     *
     * @param newCustomerRequestType New instance of customer request type
     * @return Added {@link CustomerRequestType} instance
     */
    @Override
    public CustomerRequestType addCustomerRequestType(CustomerRequestType newCustomerRequestType) {
        return customerRequestTypeDao.add(newCustomerRequestType);
    }

    /**
     * Get customer request type by id
     *
     * @param id Identifier of the desired customer request type
     * @return Found {@link CustomerRequestType} instance
     */
    @Override
    public CustomerRequestType getCustomerRequestTypeById(long id) {
        return customerRequestTypeDao.getById(id);
    }

    /**
     * Update existing  customer request type with new instance
     *
     * @param updatedCustomerRequestType Updated customer request type instance
     * @return Updated {@link CustomerRequestType} instance
     */
    @Override
    public CustomerRequestType updateCustomerRequestType(CustomerRequestType updatedCustomerRequestType) {
        return customerRequestTypeDao.update(updatedCustomerRequestType);
    }

    /**
     * Get all customer request types
     *
     * @return {@link List} instance of all customer request types
     */
    @Override
    public List<CustomerRequestType> getAllCustomerRequestTypes() {
        return customerRequestTypeDao.getAll();
    }

    /**
     * Delete customer request type by id
     *
     * @param id Identifier of the customer request type which should be deleted
     * @return Deleted {@link CustomerRequestType} instance
     */
    @Override
    public CustomerRequestType deleteCustomerRequestType(long id) {
        return customerRequestTypeDao.delete(getCustomerRequestTypeById(id));
    }
}
