package io.khasang.ba.controller;

import io.khasang.ba.entity.Customer;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static io.khasang.ba.controller.utility.MockFactory.getChangedMockCustomer;
import static io.khasang.ba.controller.utility.MockFactory.getMockCustomer;
import static io.khasang.ba.controller.utility.RestRequests.*;
import static org.junit.Assert.*;

/**
 * Integration test for Customer REST layer
 */
public class CustomerControllerIntegrationTest {

    /**
     * Check, that {@link CustomerController#getCustomerById(long)} gives NOT FOUND HTTP response
     * in the case of attempt to get nonexistent entity, i.e. entity with <em>nonexistent Id</em>.
     */
    @Test
    public void checkGetNonExistentCustomer() {
        getEntityById(Long.MAX_VALUE, Customer.class, HttpStatus.NOT_FOUND);
    }

    /**
     * Check both {@link CustomerController#getCustomerById(long)} and
     * {@link CustomerController#addCustomer(Customer)} methods,
     * i.e. HTTP methods GET and POST, providing possibilities to get an {@link Customer} entity
     * from REST resource and to add it to the resource.
     */
    @Test
    public void checkAddCustomer() {

        //POST to REST
        Customer createdCustomer = getCreatedCustomer();

        // GET from REST
        Customer receivedCustomer =
                getEntityById(
                        createdCustomer.getId(),
                        Customer.class,
                        HttpStatus.OK);

        assertEquals(createdCustomer, receivedCustomer);
    }

    /**
     * Check {@link CustomerController#getAllCustomers()} method, i.e. HTTP method GET, used to
     * get a list of {@link Customer} entities from REST resource
     * entities.<br>
     * <p>First of all, continuous addition of {@link Customer} entities with amount equal to
     * {@link io.khasang.ba.controller.utility.RestRequests#TEST_ENTITIES_AMOUNT} is performed.
     * Secondly, top TEST_ENTITIES_AMOUNT of entities, obtained from response body, received from REST-resource, placed at
     * {@link io.khasang.ba.controller.utility.RestRequests#GET_ALL_PATH}), compared with list of
     * previously added entities.
     * </p>
     */
    @Test
    public void checkGetAllCustomers() {

        // Create list of entities
        List<Customer> createdCustomersList =
                getCreatedEntitiesList(
                        Customer.class,
                        TEST_ENTITIES_AMOUNT,
                        HttpStatus.CREATED);

        // Receive all entities from REST
        List<Customer> allCustomers =
                getAllEntitiesList(Customer.class, HttpStatus.OK);

        // Check last TEST_ENTITIES_AMOUNT and assert for equality
        List<Customer> receivedCustomersSubList =
                allCustomers.subList(allCustomers.size() - TEST_ENTITIES_AMOUNT,
                        allCustomers.size());

        assertEquals(createdCustomersList, receivedCustomersSubList);
    }

    /**
     * Check {@link CustomerController#updateCustomer(Customer)}, i.e. HTTP method
     * PUT, used to update an {@link Customer} entity on REST resource
     */
    @Test
    public void checkUpdateCustomer() {

        // POST, then UPDATE in REST
        Customer updatedCustomer = getUpdatedCustomer();

        //Get it from REST, check id and assertEquals
        Customer receivedCustomer =
                getEntityById(
                        updatedCustomer.getId(),
                        Customer.class,
                        HttpStatus.OK);

        assertNotNull(receivedCustomer.getId());
        assertEquals(updatedCustomer, receivedCustomer);
    }

    /**
     * Check {@link CustomerController#deleteCustomer(long)}, i.e. HTTP method
     * DELETE, used to delete an {@link Customer} entity on REST resource
     */
    @Test
    public void checkCustomerDelete() {
        Customer createdCustomer = getCreatedCustomer();

        getResponseFromEntityDeleteRequest(
                createdCustomer.getId(),
                Customer.class,
                HttpStatus.NO_CONTENT);

        assertNull(getEntityById(
                createdCustomer.getId(),
                Customer.class,
                HttpStatus.NOT_FOUND));
    }

    //Addition constraints

    /**
     * Check unique constraint for <em>name</em> field while adding {@link Customer}
     */
    @Test
    public void checkUniqueConstraintForLogin_whenCustomerRequestStageAdd() {
        addWithIncorrectField("login", getCreatedCustomer().getLogin());
    }

    /**
     * Check unique constraint for <em>email</em> field while adding {@link Customer}
     */
    @Test
    public void checkUniqueConstraintForEmail_whenCustomerRequestStageAdd() {
        addWithIncorrectField("email", getCreatedCustomer().getEmail());
    }

    /**
     * Check not blank constraint for <em>login</em> while adding {@link Customer}
     */
    @Test
    public void checkNotBlankConstraintForLogin_whenCustomerRequestStageAdd() {
        addWithIncorrectField("login", null);
        addWithIncorrectField("login", "");
        addWithIncorrectField("login", " ");
        addWithIncorrectField("login", "  ");
        addWithIncorrectField("login", "\t");
        addWithIncorrectField("login", "\n");
    }

    /**
     * Check not blank constraint for <em>email</em> while adding {@link Customer}
     */
    @Test
    public void checkNotBlankConstraintForEmail_whenCustomerRequestStageAdd() {
        addWithIncorrectField("email", null);
        addWithIncorrectField("email", "");
        addWithIncorrectField("email", " ");
        addWithIncorrectField("email", "  ");
        addWithIncorrectField("email", "\t");
        addWithIncorrectField("email", "\n");
    }

    /**
     * Check not blank constraint for <em>password</em> while adding {@link Customer}
     */
    @Test
    public void checkNotBlankConstraintForPassword_whenCustomerRequestStageAdd() {
        addWithIncorrectField("password", null);
        addWithIncorrectField("password", "");
        addWithIncorrectField("password", " ");
        addWithIncorrectField("password", "  ");
        addWithIncorrectField("password", "\t");
        addWithIncorrectField("password", "\n");
    }

    // Update constraints

    /**
     * Check immutable constraint for <em>login</em> while updating {@link Customer}
     */
    @Test
    public void checkImmutableConstraintForLogin_whenCustomerRequestStageUpdate() {
        updateEntityWithIncorrectField(
                getCreatedCustomer(),
                "login",
                UUID.randomUUID().toString(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Check unique constraint for <em>login</em> while updating {@link Customer}
     */
    @Test
    public void checkUniqueConstraintForLogin_whenCustomerRequestStageUpdate() {
        updateWithIncorrectField("login", getCreatedCustomer().getLogin());
    }

    /**
     * Check unique constraint for <em>email</em> while updating {@link Customer}
     */
    @Test
    public void checkUniqueConstraintForEmail_whenCustomerRequestStageUpdate() {
        updateWithIncorrectField("email", getCreatedCustomer().getEmail());
    }

    /**
     * Check not blank constraint for <em>login</em> while updating {@link Customer}
     */
    @Test
    public void checkNotBlankConstraintForLogin_whenCustomerRequestStageUpdate() {
        updateWithIncorrectField("login", null);
        updateWithIncorrectField("login", "");
        updateWithIncorrectField("login", " ");
        updateWithIncorrectField("login", "  ");
        updateWithIncorrectField("login", "\t");
        updateWithIncorrectField("login", "\n");
    }

    /**
     * Check not blank constraint for <em>email</em> while updating {@link Customer}
     */
    @Test
    public void checkNotBlankConstraintForEmail_whenCustomerRequestStageUpdate() {
        updateWithIncorrectField("email", null);
        updateWithIncorrectField("email", "");
        updateWithIncorrectField("email", " ");
        updateWithIncorrectField("email", "  ");
        updateWithIncorrectField("email", "\t");
        updateWithIncorrectField("email", "\n");
    }

    /**
     * Check not blank constraint for <em>password</em> while updating {@link Customer}
     */
    @Test
    public void checkNotBlankConstraintForPassword_whenCustomerRequestStageUpdate() {
        updateWithIncorrectField("password", null);
        updateWithIncorrectField("password", "");
        updateWithIncorrectField("password", " ");
        updateWithIncorrectField("password", "  ");
        updateWithIncorrectField("password", "\t");
        updateWithIncorrectField("password", "\n");
    }

    // Utility methods

    /**
     * Create mock {@link Customer} instance, and add (i.e. POST) it to a REST resource
     *
     * @return added to REST resource entity
     */
    private Customer getCreatedCustomer() {
        Customer customer = getMockCustomer();

        Customer createdCustomer =
                getResponseFromEntityAddRequest(customer, HttpStatus.CREATED);

        assertNotNull(createdCustomer.getId());
        assertEquals(customer, createdCustomer);

        return createdCustomer;
    }

    /**
     * Update existing {@link Customer} entity at REST resource. Firstly, add new mock entity and then
     * PUT updated entity to REST resource
     *
     * @return updated at REST resource instance of entity
     */
    private Customer getUpdatedCustomer() {
        Customer createdCustomer = getCreatedCustomer();

        Customer updatedCustomer =
                getResponseFromEntityUpdateRequest(
                        getChangedMockCustomer(createdCustomer),
                        HttpStatus.OK);

        assertEquals(createdCustomer.getId(), updatedCustomer.getId());

        return updatedCustomer;
    }

    /**
     * Utility method for checking of some constraints during <em>entity addition</em>, which has simpler signature
     * with reduced number of parameters. It could be used instead of
     * direct call of {@link io.khasang.ba.controller.utility.RestRequests#addEntityWithIncorrectField(Class, String, Object, HttpStatus)}.
     *
     * @param fieldName      field, which should be set with incorrect value
     * @param incorrectValue incorrect value
     * @param <V>            type of the field
     */
    private <V> void addWithIncorrectField(String fieldName, V incorrectValue) {
        addEntityWithIncorrectField(Customer.class, fieldName, incorrectValue, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Utility method for checking of some constraints during <em>entity update</em>, which has simpler signature
     * with reduced number of parameters. It could be used instead of
     * direct call of {@link io.khasang.ba.controller.utility.RestRequests#updateEntityWithIncorrectField(Class, String, Object, HttpStatus)}.
     *
     * @param fieldName      field, which should be set with incorrect value
     * @param incorrectValue incorrect value
     * @param <V>            type of the field
     */
    private <V> void updateWithIncorrectField(String fieldName, V incorrectValue) {
        updateEntityWithIncorrectField(Customer.class, fieldName, incorrectValue, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
