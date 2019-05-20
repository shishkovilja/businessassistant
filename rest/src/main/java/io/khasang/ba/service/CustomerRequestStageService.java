package io.khasang.ba.service;

import io.khasang.ba.entity.CustomerRequestStage;

import java.util.List;

/**
 * Service layer interface for {@link CustomerRequestStage} management
 */
public interface CustomerRequestStageService {

    /**
     * Add new CustomerRequestStage
     *
     * @param newCustomerRequestStage New instance of CustomerRequestStage
     * @return Added {@link CustomerRequestStage} instance
     */
    CustomerRequestStage addCustomerRequestStage(CustomerRequestStage newCustomerRequestStage);

    /**
     * Get CustomerRequestStage by id
     *
     * @param id Identifier of the desired CustomerRequestStage
     * @return Found {@link CustomerRequestStage} instance
     */
    CustomerRequestStage getCustomerRequestStageById(long id);

    /**
     * Update existing CustomerRequestStage with new instance
     *
     * @param updatedCustomerRequestStage Updated CustomerRequestStage instance
     * @return Updated {@link CustomerRequestStage} instance
     */
    CustomerRequestStage updateCustomerRequestStage(CustomerRequestStage updatedCustomerRequestStage);

    /**
     * Get all CustomerRequestStages
     *
     * @return {@link List} instance of all CustomerRequestStages
     */
    List<CustomerRequestStage> getAllCustomerRequestStages();

    /**
     * Delete CustomerRequestStage by id
     *
     * @param id Identifier of the CustomerRequestStage which should be deleted
     * @return Deleted {@link CustomerRequestStage} instance
     */
    CustomerRequestStage deleteCustomerRequestStage(long id);
}