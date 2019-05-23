package io.khasang.ba.controller;

import io.khasang.ba.entity.Operator;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static io.khasang.ba.controller.utility.MockFactory.getChangedMockOperator;
import static io.khasang.ba.controller.utility.MockFactory.getMockOperator;
import static io.khasang.ba.controller.utility.RestRequests.*;
import static org.junit.Assert.*;

/**
 * Integration test for Operator REST layer
 */
public class OperatorControllerIntegrationTest {

    /**
     * Check, that {@link OperatorController#getOperatorById(long)} gives NOT FOUND HTTP response
     * in the case of attempt to get nonexistent entity, i.e. entity with <em>nonexistent Id</em>.
     */
    @Test
    public void checkGetNonExistentOperator() {
        getEntityById(Long.MAX_VALUE, Operator.class, HttpStatus.NOT_FOUND);
    }

    /**
     * Check both {@link OperatorController#getOperatorById(long)} and
     * {@link OperatorController#addOperator(Operator)} methods,
     * i.e. HTTP methods GET and POST, providing possibilities to get an {@link Operator} entity
     * from REST resource and to add it to the resource.
     */
    @Test
    public void checkAddOperator() {

        //POST to REST
        Operator createdOperator = getCreatedOperator();

        // GET from REST
        Operator receivedOperator =
                getEntityById(
                        createdOperator.getId(),
                        Operator.class,
                        HttpStatus.OK);

        assertEquals(createdOperator, receivedOperator);
    }

    /**
     * Check {@link OperatorController#getAllOperators()} method, i.e. HTTP method GET, used to
     * get a list of {@link Operator} entities from REST resource
     * entities.<br>
     * <p>First of all, continuous addition of {@link Operator} entities with amount equal to
     * {@link io.khasang.ba.controller.utility.RestRequests#TEST_ENTITIES_AMOUNT} is performed.
     * Secondly, top TEST_ENTITIES_AMOUNT of entities, obtained from response body, received from REST-resource, placed at
     * {@link io.khasang.ba.controller.utility.RestRequests#GET_ALL_PATH}), compared with list of
     * previously added entities.
     * </p>
     */
    @Test
    public void checkGetAllOperators() {

        // Create list of entities
        List<Operator> createdOperatorsList =
                getCreatedEntitiesList(
                        Operator.class,
                        TEST_ENTITIES_AMOUNT,
                        HttpStatus.CREATED);

        // Receive all entities from REST
        List<Operator> allOperators =
                getAllEntitiesList(Operator.class, HttpStatus.OK);

        // Check last TEST_ENTITIES_AMOUNT and assert for equality
        List<Operator> receivedOperatorsSubList =
                allOperators.subList(allOperators.size() - TEST_ENTITIES_AMOUNT,
                        allOperators.size());

        assertEquals(createdOperatorsList, receivedOperatorsSubList);
    }

    /**
     * Check {@link OperatorController#updateOperator(Operator)}, i.e. HTTP method
     * PUT, used to update an {@link Operator} entity on REST resource
     */
    @Test
    public void checkUpdateOperator() {

        // POST, then UPDATE in REST
        Operator updatedOperator = getUpdatedOperator();

        //Get it from REST, check id and assertEquals
        Operator receivedOperator =
                getEntityById(
                        updatedOperator.getId(),
                        Operator.class,
                        HttpStatus.OK);

        assertNotNull(receivedOperator.getId());
        assertEquals(updatedOperator, receivedOperator);
    }

    /**
     * Check {@link OperatorController#deleteOperator(long)}, i.e. HTTP method
     * DELETE, used to delete an {@link Operator} entity on REST resource
     */
    @Test
    public void checkOperatorDelete() {
        Operator createdOperator = getCreatedOperator();

        getResponseFromEntityDeleteRequest(
                createdOperator.getId(),
                Operator.class,
                HttpStatus.NO_CONTENT);

        assertNull(getEntityById(
                createdOperator.getId(),
                Operator.class,
                HttpStatus.NOT_FOUND));
    }

    //Addition constraints

    /**
     * Check unique constraint for <em>name</em> field while adding {@link Operator}
     */
    @Test
    public void checkUniqueConstraintForLogin_whenOperatorRequestStageAdd() {
        addWithIncorrectField("login", getCreatedOperator().getLogin());
    }

    /**
     * Check unique constraint for <em>email</em> field while adding {@link Operator}
     */
    @Test
    public void checkUniqueConstraintForEmail_whenOperatorRequestStageAdd() {
        addWithIncorrectField("email", getCreatedOperator().getEmail());
    }

    /**
     * Check not blank constraint for <em>login</em> while adding {@link Operator}
     */
    @Test
    public void checkNotBlankConstraintForLogin_whenOperatorRequestStageAdd() {
        addWithIncorrectField("login", null);
        addWithIncorrectField("login", "");
        addWithIncorrectField("login", " ");
        addWithIncorrectField("login", "  ");
        addWithIncorrectField("login", "\t");
        addWithIncorrectField("login", "\n");
    }

    /**
     * Check not blank constraint for <em>email</em> while adding {@link Operator}
     */
    @Test
    public void checkNotBlankConstraintForEmail_whenOperatorRequestStageAdd() {
        addWithIncorrectField("email", null);
        addWithIncorrectField("email", "");
        addWithIncorrectField("email", " ");
        addWithIncorrectField("email", "  ");
        addWithIncorrectField("email", "\t");
        addWithIncorrectField("email", "\n");
    }

    /**
     * Check not blank constraint for <em>password</em> while adding {@link Operator}
     */
    @Test
    public void checkNotBlankConstraintForPassword_whenOperatorRequestStageAdd() {
        addWithIncorrectField("password", null);
        addWithIncorrectField("password", "");
        addWithIncorrectField("password", " ");
        addWithIncorrectField("password", "  ");
        addWithIncorrectField("password", "\t");
        addWithIncorrectField("password", "\n");
    }

    // Update constraints

    /**
     * Check immutable constraint for <em>login</em> while updating {@link Operator}
     */
    @Test
    public void checkImmutableConstraintForLogin_whenOperatorRequestStageUpdate() {
        updateEntityWithIncorrectField(
                getCreatedOperator(),
                "login",
                UUID.randomUUID().toString(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Check unique constraint for <em>login</em> while updating {@link Operator}
     */
    @Test
    public void checkUniqueConstraintForLogin_whenOperatorRequestStageUpdate() {
        updateWithIncorrectField("login", getCreatedOperator().getLogin());
    }

    /**
     * Check unique constraint for <em>email</em> while updating {@link Operator}
     */
    @Test
    public void checkUniqueConstraintForEmail_whenOperatorRequestStageUpdate() {
        updateWithIncorrectField("email", getCreatedOperator().getEmail());
    }

    /**
     * Check not blank constraint for <em>login</em> while updating {@link Operator}
     */
    @Test
    public void checkNotBlankConstraintForLogin_whenOperatorRequestStageUpdate() {
        updateWithIncorrectField("login", null);
        updateWithIncorrectField("login", "");
        updateWithIncorrectField("login", " ");
        updateWithIncorrectField("login", "  ");
        updateWithIncorrectField("login", "\t");
        updateWithIncorrectField("login", "\n");
    }

    /**
     * Check not blank constraint for <em>email</em> while updating {@link Operator}
     */
    @Test
    public void checkNotBlankConstraintForEmail_whenOperatorRequestStageUpdate() {
        updateWithIncorrectField("email", null);
        updateWithIncorrectField("email", "");
        updateWithIncorrectField("email", " ");
        updateWithIncorrectField("email", "  ");
        updateWithIncorrectField("email", "\t");
        updateWithIncorrectField("email", "\n");
    }

    /**
     * Check not blank constraint for <em>password</em> while updating {@link Operator}
     */
    @Test
    public void checkNotBlankConstraintForPassword_whenOperatorRequestStageUpdate() {
        updateWithIncorrectField("password", null);
        updateWithIncorrectField("password", "");
        updateWithIncorrectField("password", " ");
        updateWithIncorrectField("password", "  ");
        updateWithIncorrectField("password", "\t");
        updateWithIncorrectField("password", "\n");
    }

    // Utility methods

    /**
     * Create mock {@link Operator} instance, and add (i.e. POST) it to a REST resource
     *
     * @return added to REST resource entity
     */
    private Operator getCreatedOperator() {
        Operator operator = getMockOperator();

        Operator createdOperator =
                getResponseFromEntityAddRequest(operator, HttpStatus.CREATED);

        assertNotNull(createdOperator.getId());
        assertEquals(operator, createdOperator);

        return createdOperator;
    }

    /**
     * Update existing {@link Operator} entity at REST resource. Firstly, add new mock entity and then
     * PUT updated entity to REST resource
     *
     * @return updated at REST resource instance of entity
     */
    private Operator getUpdatedOperator() {
        Operator createdOperator = getCreatedOperator();

        Operator updatedOperator =
                getResponseFromEntityUpdateRequest(
                        getChangedMockOperator(createdOperator),
                        HttpStatus.OK);

        assertEquals(createdOperator.getId(), updatedOperator.getId());

        return updatedOperator;
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
        addEntityWithIncorrectField(Operator.class, fieldName, incorrectValue, HttpStatus.INTERNAL_SERVER_ERROR);
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
        updateEntityWithIncorrectField(Operator.class, fieldName, incorrectValue, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}