package io.khasang.ba.controller;

import io.khasang.ba.entity.PointOfInterest;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.khasang.ba.controller.utility.MockFactory.getChangedMockPointOfInterest;
import static io.khasang.ba.controller.utility.MockFactory.getMockPointOfInterest;
import static io.khasang.ba.controller.utility.RestRequests.*;
import static org.junit.Assert.*;

/**
 * Integration test for PointOfInterest REST layer
 */
public class PointOfInterestControllerIntegrationTest {

    /**
     * Check, that {@link PointOfInterestController#getPointOfInterestById(long)} gives NOT FOUND HTTP response
     * in the case of attempt to get nonexistent entity, i.e. entity with <em>nonexistent Id</em>.
     */
    @Test
    public void checkGetNonExistentPointOfInterest() {
        getEntityById(Long.MAX_VALUE, PointOfInterest.class, HttpStatus.NOT_FOUND);
    }

    /**
     * Check both {@link PointOfInterestController#getPointOfInterestById(long)} and
     * {@link PointOfInterestController#addPointOfInterest(PointOfInterest)} methods,
     * i.e. HTTP methods GET and POST, providing possibilities to get an {@link PointOfInterest} entity
     * from REST resource and to add it to the resource.
     */
    @Test
    public void checkAddPointOfInterest() {

        //POST to REST
        PointOfInterest createdPointOfInterest = getCreatedPointOfInterest();

        // GET from REST
        PointOfInterest receivedPointOfInterest =
                getEntityById(
                        createdPointOfInterest.getId(),
                        PointOfInterest.class,
                        HttpStatus.OK);

        assertEquals(createdPointOfInterest, receivedPointOfInterest);
    }

    /**
     * Check {@link PointOfInterestController#getAllPointOfInterests()} method, i.e. HTTP method GET, used to
     * get a list of {@link PointOfInterest} entities from REST resource
     * entities.<br>
     * <p>First of all, continuous addition of {@link PointOfInterest} entities with amount equal to
     * {@link io.khasang.ba.controller.utility.RestRequests#TEST_ENTITIES_AMOUNT} is performed.
     * Secondly, top TEST_ENTITIES_AMOUNT of entities, obtained from response body, received from REST-resource, placed at
     * {@link io.khasang.ba.controller.utility.RestRequests#GET_ALL_PATH}), compared with list of
     * previously added entities.
     * </p>
     */
    @Test
    public void checkGetAllPointOfInterests() {

        // Create list of entities
        List<PointOfInterest> createdPointOfInterestsList =
                getCreatedEntitiesList(
                        PointOfInterest.class,
                        TEST_ENTITIES_AMOUNT,
                        HttpStatus.CREATED);

        // Receive all entities from REST
        List<PointOfInterest> allPointOfInterests =
                getAllEntitiesList(PointOfInterest.class, HttpStatus.OK);

        // Check last TEST_ENTITIES_AMOUNT and assert for equality
        List<PointOfInterest> receivedPointOfInterestsSubList =
                allPointOfInterests.subList(allPointOfInterests.size() - TEST_ENTITIES_AMOUNT,
                        allPointOfInterests.size());

        assertEquals(createdPointOfInterestsList, receivedPointOfInterestsSubList);
    }

    /**
     * Check {@link PointOfInterestController#updatePointOfInterest(PointOfInterest)}, i.e. HTTP method
     * PUT, used to update an {@link PointOfInterest} entity on REST resource
     */
    @Test
    public void checkUpdatePointOfInterest() {

        // POST, then UPDATE in REST
        PointOfInterest updatedPointOfInterest = getUpdatedPointOfInterest();

        //Get it from REST, check id and assertEquals
        PointOfInterest receivedPointOfInterest =
                getEntityById(
                        updatedPointOfInterest.getId(),
                        PointOfInterest.class,
                        HttpStatus.OK);

        assertNotNull(receivedPointOfInterest.getId());
        assertEquals(updatedPointOfInterest, receivedPointOfInterest);
    }

    /**
     * Check {@link PointOfInterestController#deletePointOfInterest(long)}, i.e. HTTP method
     * DELETE, used to delete an {@link PointOfInterest} entity on REST resource
     */
    @Test
    public void checkPointOfInterestDelete() {
        PointOfInterest createdPointOfInterest = getCreatedPointOfInterest();

        getResponseFromEntityDeleteRequest(
                createdPointOfInterest.getId(),
                PointOfInterest.class,
                HttpStatus.NO_CONTENT);

        assertNull(getEntityById(
                createdPointOfInterest.getId(),
                PointOfInterest.class,
                HttpStatus.NOT_FOUND));
    }

    //Addition constraints

    /**
     * Check not blank constraint for <em>name</em> while adding {@link PointOfInterest}
     */
    @Test
    public void checkNotBlankConstraintForName_whenPointOfInterestRequestStageAdd() {
        addWithIncorrectField("name", null);
        addWithIncorrectField("name", "");
        addWithIncorrectField("name", " ");
        addWithIncorrectField("name", "  ");
        addWithIncorrectField("name", "\t");
        addWithIncorrectField("name", "\n");
    }

    // Update constraints

    /**
     * Check not blank constraint for <em>name</em> while updating {@link PointOfInterest}
     */
    @Test
    public void checkNotBlankConstraintForName_whenPointOfInterestRequestStageUpdate() {
        updateWithIncorrectField("name", null);
        updateWithIncorrectField("name", "");
        updateWithIncorrectField("name", " ");
        updateWithIncorrectField("name", "  ");
        updateWithIncorrectField("name", "\t");
        updateWithIncorrectField("name", "\n");
    }

    // Utility methods

    /**
     * Create mock {@link PointOfInterest} instance, and add (i.e. POST) it to a REST resource
     *
     * @return added to REST resource entity
     */
    private PointOfInterest getCreatedPointOfInterest() {
        PointOfInterest pointOfInterest = getMockPointOfInterest();

        PointOfInterest createdPointOfInterest =
                getResponseFromEntityAddRequest(pointOfInterest, HttpStatus.CREATED);

        assertNotNull(createdPointOfInterest.getId());
        pointOfInterest.setId(createdPointOfInterest.getId());
        assertEquals(pointOfInterest, createdPointOfInterest);

        return createdPointOfInterest;
    }

    /**
     * Update existing {@link PointOfInterest} entity at REST resource. Firstly, add new mock entity and then
     * PUT updated entity to REST resource
     *
     * @return updated at REST resource instance of entity
     */
    private PointOfInterest getUpdatedPointOfInterest() {
        PointOfInterest createdPointOfInterest = getCreatedPointOfInterest();

        PointOfInterest updatedPointOfInterest =
                getResponseFromEntityUpdateRequest(
                        getChangedMockPointOfInterest(createdPointOfInterest),
                        HttpStatus.OK);

        assertEquals(createdPointOfInterest.getId(), updatedPointOfInterest.getId());

        return updatedPointOfInterest;
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
        addEntityWithIncorrectField(PointOfInterest.class, fieldName, incorrectValue, HttpStatus.INTERNAL_SERVER_ERROR);
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
        updateEntityWithIncorrectField(PointOfInterest.class, fieldName, incorrectValue, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
