package io.khasang.ba.controller;

import io.khasang.ba.controller.utility.MockFactory;
import io.khasang.ba.controller.utility.RestRequests;
import io.khasang.ba.entity.CustomerRequest;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.khasang.ba.controller.utility.MockFactory.getChangedMockCustomerRequest;
import static io.khasang.ba.controller.utility.MockFactory.getMockCustomerRequest;
import static io.khasang.ba.controller.utility.RestRequests.*;
import static org.junit.Assert.*;

/**
 * Integration test for {@link CustomerRequestController} and {@link CustomerRequest} REST layer.
 * Utility classes {@link MockFactory} and {@link io.khasang.ba.controller.utility.RestRequests} are used.
 */
// TODO DELETE nonexistent
// TODO UPDATE nonexistent
// TODO provide Error messages
public class CustomerRequestControllerIntegrationTest {

    /**
     * Check, that {@link CustomerRequestController#getCustomerRequestById(long)} gives NOT FOUND HTTP response
     * in the case of attempt to get nonexistent entity, i.e. entity with <em>nonexistent Id</em>.
     */
    @Test
    public void checkGetNonExistentCustomerRequest() {
        getEntityById(Long.MAX_VALUE, CustomerRequest.class, HttpStatus.NOT_FOUND);
    }

    /**
     * Check both {@link CustomerRequestController#getCustomerRequestById(long)} and
     * {@link CustomerRequestController#addCustomerRequest(CustomerRequest)} methods,
     * i.e. HTTP methods GET and POST, providing possibilities to get an {@link CustomerRequest} entity
     * from REST resource and to add it to the resource.
     */
    @Test
    public void checkAddCustomerRequest() {

        //POST to REST
        CustomerRequest createdCustomerRequest = getCreatedCustomerRequest();

        // GET from REST
        CustomerRequest receivedCustomerRequest =
                getEntityById(
                        createdCustomerRequest.getId(),
                        CustomerRequest.class,
                        HttpStatus.OK);

        assertEquals(createdCustomerRequest, receivedCustomerRequest);
    }

    /**
     * Check {@link CustomerRequestController#getAllCustomerRequests()} method, i.e. HTTP method GET, used to
     * get a list of {@link CustomerRequest} entities from REST resource
     * entities.<br>
     * <p>First of all, continuous addition of {@link CustomerRequest} entities with amount equal to
     * {@link io.khasang.ba.controller.utility.RestRequests#TEST_ENTITIES_AMOUNT} is performed.
     * Secondly, top TEST_ENTITIES_AMOUNT of entities, obtained from response body, received from REST-resource, placed at
     * {@link io.khasang.ba.controller.utility.RestRequests#GET_ALL_PATH}), compared with list of
     * previously added entities.
     * </p>
     */
    @Test
    public void checkGetAllCustomerRequests() {

        // Create list of entities
        List<CustomerRequest> createdCustomerRequestsList =
                getCreatedEntitiesList(
                        CustomerRequest.class,
                        RestRequests.TEST_ENTITIES_AMOUNT,
                        HttpStatus.CREATED);

        // Receive all entities from REST
        List<CustomerRequest> allCustomerRequests =
                getAllEntitiesList(CustomerRequest.class, HttpStatus.OK);

        // Check last TEST_ENTITIES_AMOUNT and assert for equality
        List<CustomerRequest> receivedCustomerRequestsSubList =
                allCustomerRequests.subList(allCustomerRequests.size() - TEST_ENTITIES_AMOUNT,
                        allCustomerRequests.size());

        assertEquals(createdCustomerRequestsList, receivedCustomerRequestsSubList);
    }

    /**
     * Check {@link CustomerRequestController#updateCustomerRequest(CustomerRequest)}, i.e. HTTP method
     * PUT, used to update an {@link CustomerRequest} entity on REST resource
     */
    @Test
    public void checkUpdateCustomerRequest() {

        // POST, then UPDATE in REST
        CustomerRequest updatedCustomerRequest = getUpdatedCustomerRequest();

        //Get it from REST, check id and assertEquals
        CustomerRequest receivedCustomerRequest =
                getEntityById(
                        updatedCustomerRequest.getId(),
                        CustomerRequest.class,
                        HttpStatus.OK);

        assertNotNull(receivedCustomerRequest.getId());
        assertEquals(updatedCustomerRequest, receivedCustomerRequest);
    }

    /**
     * Check {@link CustomerRequestController#deleteCustomerRequest(long)}, i.e. HTTP method
     * DELETE, used to delete an {@link CustomerRequest} entity on REST resource
     */
    @Test
    public void checkCustomerRequestDelete() {
        CustomerRequest createdCustomerRequest = getCreatedCustomerRequest();
        getResponseFromEntityDeleteRequest(
                createdCustomerRequest.getId(),
                CustomerRequest.class,
                HttpStatus.NO_CONTENT);

        assertNull(getEntityById(
                createdCustomerRequest.getId(),
                CustomerRequest.class,
                HttpStatus.NOT_FOUND));
    }

    // Utility methods

    /**
     * Create mock {@link CustomerRequest} instance, and add (i.e. POST) it to a REST resource
     *
     * @return added to REST resource entity
     */
    private CustomerRequest getCreatedCustomerRequest() {
        CustomerRequest customerRequest = getMockCustomerRequest();

        CustomerRequest createdCustomerRequest =
                getResponseFromEntityAddRequest(customerRequest, HttpStatus.CREATED);

        assertNotNull(createdCustomerRequest.getId());
        assertEquals(customerRequest, createdCustomerRequest);

        return createdCustomerRequest;
    }

    /**
     * Update existing {@link CustomerRequest} entity at REST resource. Firstly, add new mock entity and then
     * PUT updated entity to REST resource
     *
     * @return updated at REST resource instance of entity
     */
    private CustomerRequest getUpdatedCustomerRequest() {
        CustomerRequest createdCustomerRequest = getCreatedCustomerRequest();

        CustomerRequest updatedCustomerRequest =
                getResponseFromEntityUpdateRequest(
                        getChangedMockCustomerRequest(createdCustomerRequest),
                        HttpStatus.OK);

        assertEquals(createdCustomerRequest.getId(), updatedCustomerRequest.getId());

        return updatedCustomerRequest;
    }
}
