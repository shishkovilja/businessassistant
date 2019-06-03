package io.khasang.ba.service;

import io.khasang.ba.entity.CustomerRequestStageName;

import java.util.List;

/**
 * Service layer interface for {@link CustomerRequestStageName} management
 */
public interface CustomerRequestStageNameService {

    /**
     * Add new CustomerRequestStageName
     *
     * @param newCustomerRequestStageName New instance of CustomerRequestStageName
     * @return Added {@link CustomerRequestStageName} instance
     */
    CustomerRequestStageName addCustomerRequestStageName(CustomerRequestStageName newCustomerRequestStageName);

    /**
     * Get CustomerRequestStageName by id
     *
     * @param id Identifier of the desired CustomerRequestStageName
     * @return Found {@link CustomerRequestStageName} instance
     */
    CustomerRequestStageName getCustomerRequestStageNameById(long id);

    /**
     * Update existing CustomerRequestStageName with new instance
     *
     * @param updatedCustomerRequestStageName Updated CustomerRequestStageName instance
     * @return Updated {@link CustomerRequestStageName} instance
     */
    CustomerRequestStageName updateCustomerRequestStageName(CustomerRequestStageName updatedCustomerRequestStageName);

    /**
     * Get all CustomerRequestStageNames
     *
     * @return {@link List} instance of all CustomerRequestStageNames
     */
    List<CustomerRequestStageName> getAllCustomerRequestStageNames();

    /**
     * Delete CustomerRequestStageName by id
     *
     * @param id Identifier of the CustomerRequestStageName which should be deleted
     * @return Deleted {@link CustomerRequestStageName} instance
     */
    CustomerRequestStageName deleteCustomerRequestStageName(long id);
}