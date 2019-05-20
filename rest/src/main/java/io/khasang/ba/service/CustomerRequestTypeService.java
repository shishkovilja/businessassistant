package io.khasang.ba.service;

import io.khasang.ba.entity.CustomerRequestType;

import java.util.List;

/**
 * Service layer interface for customer request types management
 */
public interface CustomerRequestTypeService {

    /**
     * Add new customer request type
     *
     * @param newCustomerRequestType New instance of customer request type
     * @return Added {@link CustomerRequestType} instance
     */
    CustomerRequestType addCustomerRequestType(CustomerRequestType newCustomerRequestType);

    /**
     * Get customer request type by id
     *
     * @param id Identifier of the desired customer request type
     * @return Found {@link CustomerRequestType} instance
     */
    CustomerRequestType getCustomerRequestTypeById(long id);

    /**
     * Update existing customer request type with new instance
     *
     * @param updatedCustomerRequestType Updated customer request type instance
     * @return Updated {@link CustomerRequestType} instance
     */
    CustomerRequestType updateCustomerRequestType(CustomerRequestType updatedCustomerRequestType);

    /**
     * Get all customer request types
     *
     * @return {@link List} instance of all customer request types
     */
    List<CustomerRequestType> getAllCustomerRequestTypes();

    /**
     * Delete customer request type by id
     *
     * @param id Identifier of the customer request type which should be deleted
     * @return Deleted {@link CustomerRequestType} instance
     */
    CustomerRequestType deleteCustomerRequestType(long id);
}
