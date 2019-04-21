package io.khasang.ba.service;

import io.khasang.ba.entity.Customer;

import java.util.List;

/**
 * Service layer interface for Customer management
 */
public interface CustomerService {

    /**
     * Add new Customer
     *
     * @param newCustomer New instance of Customer
     * @return Added {@link Customer} instance
     */
    Customer addCustomer(Customer newCustomer);

    /**
     * Get Customer by id
     *
     * @param id Identifier of the desired Customer
     * @return Found {@link Customer} instance
     */
    Customer getCustomerById(long id);

    /**
     * Update existing Customer with new instance
     *
     * @param updatedCustomer Updated Customer instance
     * @return Updated {@link Customer} instance
     */
    Customer updateCustomer(Customer updatedCustomer);

    /**
     * Get all Customers
     *
     * @return {@link List} instance of all Customers
     */
    List<Customer> getAllCustomers();

    /**
     * Delete Customer by id
     *
     * @param id Identifier of the Customer which should be deleted
     * @return Deleted {@link Customer} instance
     */
    Customer deleteCustomer(long id);
}
