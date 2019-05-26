package io.khasang.ba.service.impl;

import io.khasang.ba.dao.CustomerRequestStageNameDao;
import io.khasang.ba.entity.CustomerRequestStageName;
import io.khasang.ba.service.CustomerRequestStageNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link CustomerRequestStageNameService} based on DAO-layer utilization
 */
@Service
public class CustomerRequestStageNameServiceImpl implements CustomerRequestStageNameService {

    @Autowired
    private CustomerRequestStageNameDao CustomerRequestStageNameDao;

    /**
     * Add new CustomerRequestStageName
     *
     * @param newCustomerRequestStageName New instance of CustomerRequestStageName
     * @return Added {@link CustomerRequestStageName} instance
     */
    @Override
    public CustomerRequestStageName addCustomerRequestStageName(CustomerRequestStageName newCustomerRequestStageName) {
        return CustomerRequestStageNameDao.add(newCustomerRequestStageName);
    }

    /**
     * Get CustomerRequestStageName by id
     *
     * @param id Identifier of the desired CustomerRequestStageName
     * @return Found {@link CustomerRequestStageName} instance
     */
    @Override
    public CustomerRequestStageName getCustomerRequestStageNameById(long id) {
        return CustomerRequestStageNameDao.getById(id);
    }

    /**
     * Update existing CustomerRequestStageName with new instance
     *
     * @param updatedCustomerRequestStageName Updated CustomerRequestStageName instance
     * @return Updated {@link CustomerRequestStageName} instance
     */
    @Override
    public CustomerRequestStageName updateCustomerRequestStageName(CustomerRequestStageName updatedCustomerRequestStageName) {
        return CustomerRequestStageNameDao.update(updatedCustomerRequestStageName);
    }

    /**
     * Get all CustomerRequestStageNames
     *
     * @return {@link List} instance of all CustomerRequestStageNames
     */
    @Override
    public List<CustomerRequestStageName> getAllCustomerRequestStageNames() {
        return CustomerRequestStageNameDao.getAll();
    }

    /**
     * Delete CustomerRequestStageName by id
     *
     * @param id Identifier of the CustomerRequestStageName which should be deleted
     * @return Deleted {@link CustomerRequestStageName} instance
     */
    @Override
    public CustomerRequestStageName deleteCustomerRequestStageName(long id) {
        return CustomerRequestStageNameDao.delete(getCustomerRequestStageNameById(id));
    }
}
