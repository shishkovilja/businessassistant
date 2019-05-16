package io.khasang.ba.service.impl;

import io.khasang.ba.dao.CustomerRequestStageDao;
import io.khasang.ba.entity.CustomerRequestStage;
import io.khasang.ba.service.CustomerRequestStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of {@link CustomerRequestStageService} based on DAO-layer utilization
 */
@Service
public class CustomerRequestStageServiceImpl implements CustomerRequestStageService {

    @Autowired
    private CustomerRequestStageDao customerRequestStageDao;

    /**
     * Add new CustomerRequestStage
     *
     * @param newCustomerRequestStage New instance of CustomerRequestStage
     * @return Added {@link CustomerRequestStage} instance
     */
    @Override
    public CustomerRequestStage addCustomerRequestStage(CustomerRequestStage newCustomerRequestStage) {

        //TODO Make trigger in database for creation event
        newCustomerRequestStage.setCreationTimestamp(LocalDateTime.now());
        return customerRequestStageDao.add(newCustomerRequestStage);
    }

    /**
     * Get CustomerRequestStage by id
     *
     * @param id Identifier of the desired CustomerRequestStage
     * @return Found {@link CustomerRequestStage} instance
     */
    @Override
    public CustomerRequestStage getCustomerRequestStageById(long id) {
        return customerRequestStageDao.getById(id);
    }

    /**
     * Update existing CustomerRequestStage with new instance
     *
     * @param updatedCustomerRequestStage Updated CustomerRequestStage instance
     * @return Updated {@link CustomerRequestStage} instance
     */
    @Override
    public CustomerRequestStage updateCustomerRequestStage(CustomerRequestStage updatedCustomerRequestStage) {

        //TODO Make trigger in database for update event
        updatedCustomerRequestStage.setUpdateTimestamp(LocalDateTime.now());
        return customerRequestStageDao.update(updatedCustomerRequestStage);
    }

    /**
     * Get all CustomerRequestStages
     *
     * @return {@link List} instance of all CustomerRequestStages
     */
    @Override
    public List<CustomerRequestStage> getAllCustomerRequestStages() {
        return customerRequestStageDao.getAll();
    }

    /**
     * Delete CustomerRequestStage by id
     *
     * @param id Identifier of the CustomerRequestStage which should be deleted
     * @return Deleted {@link CustomerRequestStage} instance
     */
    @Override
    public CustomerRequestStage deleteCustomerRequestStage(long id) {
        return customerRequestStageDao.delete(getCustomerRequestStageById(id));
    }
}
