package io.khasang.ba.controller;

import io.khasang.ba.controller.utility.MockFactory;
import io.khasang.ba.entity.CustomerRequestStage;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static io.khasang.ba.controller.utility.MockFactory.getChangedMockCustomerRequestStage;
import static io.khasang.ba.controller.utility.MockFactory.getMockCustomerRequestStage;
import static io.khasang.ba.controller.utility.RestRequests.*;
import static org.junit.Assert.*;

/**
 * Integration test for {@link CustomerRequestStageController} and {@link CustomerRequestStage} REST layer.
 * Utility classes {@link MockFactory} and {@link io.khasang.ba.controller.utility.RestRequests} are used.
 */
// TODO DELETE nonexistent
// TODO UPDATE nonexistent
public class CustomerRequestStageControllerIntegrationTest {

    /**
     * Check, that {@link CustomerRequestStageController#getCustomerRequestStageById(long)} gives NOT FOUND HTTP response
     * in the case of attempt to get nonexistent entity, i.e. entity with <em>nonexistent Id</em>.
     */
    // TODO provide Error messages
    @Test
    public void checkGetNonExistentCustomerRequestStage() {
        getEntityById(
                Long.MAX_VALUE,
                CustomerRequestStage.class,
                HttpStatus.NOT_FOUND);
    }

    /**
     * Check both {@link CustomerRequestStageController#getCustomerRequestStageById(long)} and
     * {@link CustomerRequestStageController#addCustomerRequestStage(CustomerRequestStage)} methods,
     * i.e. HTTP methods GET and POST, providing possibilities to get an {@link CustomerRequestStage} entity
     * from REST resource and to add it to the resource.
     */
    @Test
    public void checkAddCustomerRequestStage() {

        //POST to REST
        CustomerRequestStage createdCustomerRequestStage = getCreatedCustomerRequestStage();

        // GET from REST
        CustomerRequestStage receivedCustomerRequestStage = getEntityById(
                createdCustomerRequestStage.getId(),
                CustomerRequestStage.class,
                HttpStatus.OK);

        assertEquals(createdCustomerRequestStage, receivedCustomerRequestStage);
    }

    /**
     * Check {@link CustomerRequestStageController#getAllCustomerRequestStages()} method, i.e. HTTP method GET, used to
     * get a list of {@link CustomerRequestStage} entities from REST resource
     * entities.<br>
     * <p>First of all, continuous addition of {@link CustomerRequestStage} entities with amount equal to
     * {@link io.khasang.ba.controller.utility.RestRequests#TEST_ENTITIES_AMOUNT} is performed.
     * Secondly, top TEST_ENTITIES_AMOUNT of entities, obtained from response body, received from REST-resource, placed at
     * {@link io.khasang.ba.controller.utility.RestRequests#GET_ALL_PATH}), compared with list of
     * previously added entities.
     * </p>
     */
    @Test
    public void checkGetAllCustomerRequestStages() {

        // Create list of entities
        List<CustomerRequestStage> createdCustomerRequestStagesList =
                getCreatedEntitiesList(
                        CustomerRequestStage.class,
                        TEST_ENTITIES_AMOUNT,
                        HttpStatus.CREATED);

        // Receive all entities from REST
        List<CustomerRequestStage> allCustomerRequestStages =
                getAllEntitiesList(CustomerRequestStage.class, HttpStatus.OK);

        // Check last TEST_ENTITIES_AMOUNT and assert for equality
        List<CustomerRequestStage> receivedCustomerRequestStagesSubList =
                allCustomerRequestStages.subList(allCustomerRequestStages.size() - TEST_ENTITIES_AMOUNT,
                        allCustomerRequestStages.size());

        assertEquals(createdCustomerRequestStagesList, receivedCustomerRequestStagesSubList);
    }

    /**
     * Check {@link CustomerRequestStageController#updateCustomerRequestStage(CustomerRequestStage)}, i.e. HTTP method
     * PUT, used to update an {@link CustomerRequestStage} entity on REST resource
     */
    @Test
    public void checkUpdateCustomerRequestStage() {

        // POST, then UPDATE in REST
        CustomerRequestStage updatedCustomerRequestStage = getUpdatedCustomerRequestStage();

        //Get it from REST, check id and assertEquals
        CustomerRequestStage receivedCustomerRequestStage = getEntityById(
                updatedCustomerRequestStage.getId(),
                CustomerRequestStage.class,
                HttpStatus.OK
        );
        assertNotNull(receivedCustomerRequestStage.getId());
        assertEquals(updatedCustomerRequestStage, receivedCustomerRequestStage);
    }

    /**
     * Check {@link CustomerRequestStageController#deleteCustomerRequestStage(long)}, i.e. HTTP method
     * DELETE, used to delete an {@link CustomerRequestStage} entity on REST resource
     */
    @Test
    public void checkCustomerRequestStageDelete() {
        CustomerRequestStage createdCustomerRequestStage = getCreatedCustomerRequestStage();
        getResponseFromEntityDeleteRequest(
                createdCustomerRequestStage.getId(),
                CustomerRequestStage.class,
                HttpStatus.NO_CONTENT);

        assertNull(getEntityById(
                createdCustomerRequestStage.getId(),
                CustomerRequestStage.class,
                HttpStatus.NOT_FOUND));
    }

    /**
     * Create mock {@link CustomerRequestStage} instance, and add (i.e. POST) it to a REST resource
     *
     * @return added to REST resource entity
     */
    private CustomerRequestStage getCreatedCustomerRequestStage() {
        CustomerRequestStage customerRequestStage = getMockCustomerRequestStage();

        LocalDateTime timeBeforeCreation = LocalDateTime.now();
        CustomerRequestStage createdCustomerRequestStage = getResponseFromEntityAddRequest(
                customerRequestStage,
                HttpStatus.CREATED);
        LocalDateTime timeAfterCreation = LocalDateTime.now();

        assertNotNull(createdCustomerRequestStage.getId());
        assertEquals(customerRequestStage, createdCustomerRequestStage);
        assertEquals(-1, timeBeforeCreation.compareTo(createdCustomerRequestStage.getCreationTimestamp()));
        assertEquals(1, timeAfterCreation.compareTo(createdCustomerRequestStage.getCreationTimestamp()));

        return createdCustomerRequestStage;
    }

    /**
     * Update existing {@link CustomerRequestStage} entity at REST resource. Firstly, add new mock entity and then
     * PUT updated entity to REST resource
     *
     * @return updated at REST resource instance of entity
     */
    private CustomerRequestStage getUpdatedCustomerRequestStage() {
        CustomerRequestStage createdCustomerRequestStage = getCreatedCustomerRequestStage();

        LocalDateTime timeBeforeUpdate = LocalDateTime.now();
        CustomerRequestStage updatedCustomerRequestStage =
                getResponseFromEntityUpdateRequest(
                        getChangedMockCustomerRequestStage(createdCustomerRequestStage),
                        HttpStatus.OK);
        LocalDateTime timeAfterUpdate = LocalDateTime.now();

        assertEquals(createdCustomerRequestStage.getId(), updatedCustomerRequestStage.getId());
        assertEquals(-1, timeBeforeUpdate.compareTo(updatedCustomerRequestStage.getUpdateTimestamp()));
        assertEquals(1, timeAfterUpdate.compareTo(updatedCustomerRequestStage.getUpdateTimestamp()));

        return updatedCustomerRequestStage;
    }
}
