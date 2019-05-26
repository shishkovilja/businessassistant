package io.khasang.ba.service;

import io.khasang.ba.entity.CustomerRequest;

import java.util.List;

public interface CustomerRequestService {
    /**
     * method for add request
     *
     * @param customerRequest = request for adding
     * @return created Request
     */
    CustomerRequest addCustomerRequest(CustomerRequest customerRequest);

    /**
     * method for getting request by specific id
     *
     * @param id - request's id
     * @return request by id
     */
    CustomerRequest getCustomerRequestById(long id);

    /**
     * method for getting all requests
     *
     * @return all requests
     */
    List<CustomerRequest> getAllCustomerRequests();

    /**
     * method for update request
     *
     * @param customerRequest - request's with updated params
     * @return updated request
     */
    CustomerRequest updateCustomerRequest(CustomerRequest customerRequest);

    /**
     * method for delete request by id
     *
     * @param id - request's id for delete
     * @return deleted request
     */
    CustomerRequest deleteCustomerRequest(long id);
}
