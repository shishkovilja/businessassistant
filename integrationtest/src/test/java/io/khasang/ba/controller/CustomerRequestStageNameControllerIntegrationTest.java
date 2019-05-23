package io.khasang.ba.controller;

import io.khasang.ba.controller.utility.MockFactory;
import io.khasang.ba.controller.utility.RestRequests;
import io.khasang.ba.entity.CustomerRequestStageName;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.khasang.ba.controller.utility.MockFactory.getChangedMockCustomerRequestStageName;
import static io.khasang.ba.controller.utility.MockFactory.getMockCustomerRequestStageName;
import static io.khasang.ba.controller.utility.RestRequests.*;
import static org.junit.Assert.*;

/**
 * Integration test for {@link CustomerRequestStageNameController} and {@link CustomerRequestStageName} REST layer.
 * Utility classes {@link MockFactory} and {@link io.khasang.ba.controller.utility.RestRequests} are used.
 */
// TODO DELETE nonexistent
// TODO UPDATE nonexistent
// TODO provide Error messages
public class CustomerRequestStageNameControllerIntegrationTest {

    /**
     * Check, that {@link CustomerRequestStageNameController#getCustomerRequestStageNameById(long)} gives NOT FOUND HTTP response
     * in the case of attempt to get nonexistent entity, i.e. entity with <em>nonexistent Id</em>.
     */
    @Test
    public void checkGetNonExistentCustomerRequestStageName() {
        getEntityById(Long.MAX_VALUE, CustomerRequestStageName.class, HttpStatus.NOT_FOUND);
    }

    /**
     * Check both {@link CustomerRequestStageNameController#getCustomerRequestStageNameById(long)} and
     * {@link CustomerRequestStageNameController#addCustomerRequestStageName(CustomerRequestStageName)} methods,
     * i.e. HTTP methods GET and POST, providing possibilities to get an {@link CustomerRequestStageName} entity
     * from REST resource and to add it to the resource.
     */
    @Test
    public void checkAddCustomerRequestStageName() {

        //POST to REST
        CustomerRequestStageName createdCustomerRequestStageName = getCreatedCustomerRequestStageName();

        // GET from REST
        CustomerRequestStageName receivedCustomerRequestStageName =
                getEntityById(
                        createdCustomerRequestStageName.getId(),
                        CustomerRequestStageName.class,
                        HttpStatus.OK);

        assertEquals(createdCustomerRequestStageName, receivedCustomerRequestStageName);
    }

    /**
     * Check {@link CustomerRequestStageNameController#getAllCustomerRequestStageNames()} method, i.e. HTTP method GET, used to
     * get a list of {@link CustomerRequestStageName} entities from REST resource
     * entities.<br>
     * <p>First of all, continuous addition of {@link CustomerRequestStageName} entities with amount equal to
     * {@link io.khasang.ba.controller.utility.RestRequests#TEST_ENTITIES_AMOUNT} is performed.
     * Secondly, top TEST_ENTITIES_AMOUNT of entities, obtained from response body, received from REST-resource, placed at
     * {@link io.khasang.ba.controller.utility.RestRequests#GET_ALL_PATH}), compared with list of
     * previously added entities.
     * </p>
     */
    @Test
    public void checkGetAllCustomerRequestStageNames() {

        // Create list of entities
        List<CustomerRequestStageName> createdCustomerRequestStageNamesList =
                getCreatedEntitiesList(
                        CustomerRequestStageName.class,
                        RestRequests.TEST_ENTITIES_AMOUNT,
                        HttpStatus.CREATED);

        // Receive all entities from REST
        List<CustomerRequestStageName> allCustomerRequestStageNames =
                getAllEntitiesList(CustomerRequestStageName.class, HttpStatus.OK);

        // Check last TEST_ENTITIES_AMOUNT and assert for equality
        List<CustomerRequestStageName> receivedCustomerRequestStageNamesSubList =
                allCustomerRequestStageNames.subList(allCustomerRequestStageNames.size() - TEST_ENTITIES_AMOUNT,
                        allCustomerRequestStageNames.size());

        assertEquals(createdCustomerRequestStageNamesList, receivedCustomerRequestStageNamesSubList);
    }

    /**
     * Check {@link CustomerRequestStageNameController#updateCustomerRequestStageName(CustomerRequestStageName)}, i.e. HTTP method
     * PUT, used to update an {@link CustomerRequestStageName} entity on REST resource
     */
    @Test
    public void checkUpdateCustomerRequestStageName() {

        // POST, then UPDATE in REST
        CustomerRequestStageName updatedCustomerRequestStageName = getUpdatedCustomerRequestStageName();

        //Get it from REST, check id and assertEquals
        CustomerRequestStageName receivedCustomerRequestStageName =
                getEntityById(
                        updatedCustomerRequestStageName.getId(),
                        CustomerRequestStageName.class,
                        HttpStatus.OK);

        assertNotNull(receivedCustomerRequestStageName.getId());
        assertEquals(updatedCustomerRequestStageName, receivedCustomerRequestStageName);
    }

    /**
     * Check {@link CustomerRequestStageNameController#deleteCustomerRequestStageName(long)}, i.e. HTTP method
     * DELETE, used to delete an {@link CustomerRequestStageName} entity on REST resource
     */
    @Test
    public void checkCustomerRequestStageNameDelete() {
        CustomerRequestStageName createdCustomerRequestStageName = getCreatedCustomerRequestStageName();
        getResponseFromEntityDeleteRequest(
                createdCustomerRequestStageName.getId(),
                CustomerRequestStageName.class,
                HttpStatus.NO_CONTENT);

        assertNull(getEntityById(
                createdCustomerRequestStageName.getId(),
                CustomerRequestStageName.class,
                HttpStatus.NOT_FOUND));
    }

    // Addition constraints

    /**
     * Check unique constraint for <em>name</em> field while adding {@link CustomerRequestStageName}
     */
    @Test
    public void checkUniqueConstraintForName_whenCustomerRequestStageAdd() {
        addWithIncorrectField("name", getCreatedCustomerRequestStageName().getName());
    }

    /**
     * Check not blank constraint for <em>name</em> while adding {@link CustomerRequestStageName}
     */
    @Test
    public void checkNotBlankConstraintForName_whenCustomerRequestStageAdd() {
        addWithIncorrectField("name", null);
        addWithIncorrectField("name", "");
        addWithIncorrectField("name", " ");
        addWithIncorrectField("name", "  ");
        addWithIncorrectField("name", "\t");
        addWithIncorrectField("name", "\n");
    }

    // Update constraints

    /**
     * Check unique constraint for <em>name</em> while updating {@link CustomerRequestStageName}
     */
    @Test
    public void checkUniqueConstraintForName_whenCustomerRequestStageUpdate() {
        updateWithIncorrectField("name", getCreatedCustomerRequestStageName().getName());
    }

    /**
     * Check not blank constraint for <em>name</em> while updating {@link CustomerRequestStageName}
     */
    @Test
    public void checkNotBlankConstraintForName_whenCustomerRequestStageUpdate() {
        updateWithIncorrectField("name", null);
        updateWithIncorrectField("name", "");
        updateWithIncorrectField("name", " ");
        updateWithIncorrectField("name", "  ");
        updateWithIncorrectField("name", "\t");
        updateWithIncorrectField("name", "\n");
    }

    // Utility methods

    /**
     * Create mock {@link CustomerRequestStageName} instance, and add (i.e. POST) it to a REST resource
     *
     * @return added to REST resource entity
     */
    private CustomerRequestStageName getCreatedCustomerRequestStageName() {
        CustomerRequestStageName customerRequestStageName = getMockCustomerRequestStageName();

        CustomerRequestStageName createdCustomerRequestStageName =
                getResponseFromEntityAddRequest(customerRequestStageName, HttpStatus.CREATED);

        assertNotNull(createdCustomerRequestStageName.getId());
        assertEquals(customerRequestStageName, createdCustomerRequestStageName);

        return createdCustomerRequestStageName;
    }

    /**
     * Update existing {@link CustomerRequestStageName} entity at REST resource. Firstly, add new mock entity and then
     * PUT updated entity to REST resource
     *
     * @return updated at REST resource instance of entity
     */
    private CustomerRequestStageName getUpdatedCustomerRequestStageName() {
        CustomerRequestStageName createdCustomerRequestStageName = getCreatedCustomerRequestStageName();

        CustomerRequestStageName updatedCustomerRequestStageName =
                getResponseFromEntityUpdateRequest(
                        getChangedMockCustomerRequestStageName(createdCustomerRequestStageName),
                        HttpStatus.OK);

        assertEquals(createdCustomerRequestStageName.getId(), updatedCustomerRequestStageName.getId());

        return updatedCustomerRequestStageName;
    }

    /**
     * Utility method for checking of some constraints during <em>entity addition</em>, which has simpler signature
     * with reduced number of parameters. It could be used instead of
     * direct call of {@link RestRequests#addEntityWithIncorrectField(Class, String, Object, HttpStatus)}.
     *
     * @param fieldName      field, which should be set with incorrect value
     * @param incorrectValue incorrect value
     * @param <V>            type of the field
     */
    private <V> void addWithIncorrectField(String fieldName, V incorrectValue) {
        addEntityWithIncorrectField(CustomerRequestStageName.class, fieldName, incorrectValue, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Utility method for checking of some constraints during <em>entity update</em>, which has simpler signature
     * with reduced number of parameters. It could be used instead of
     * direct call of {@link RestRequests#updateEntityWithIncorrectField(Class, String, Object, HttpStatus)}.
     *
     * @param fieldName      field, which should be set with incorrect value
     * @param incorrectValue incorrect value
     * @param <V>            type of the field
     */
    private <V> void updateWithIncorrectField(String fieldName, V incorrectValue) {
        updateEntityWithIncorrectField(CustomerRequestStageName.class, fieldName, incorrectValue, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
