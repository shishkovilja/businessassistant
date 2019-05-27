package io.khasang.ba.controller;

import io.khasang.ba.entity.Category;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.khasang.ba.controller.utility.MockFactory.getChangedMockCategory;
import static io.khasang.ba.controller.utility.MockFactory.getMockCategory;
import static io.khasang.ba.controller.utility.RestRequests.*;
import static org.junit.Assert.*;

/**
 * Integration test for Category REST layer
 */
public class CategoryControllerIntegrationTest {

    /**
     * Check, that {@link CategoryController#getCategoryById(long)} gives NOT FOUND HTTP response
     * in the case of attempt to get nonexistent entity, i.e. entity with <em>nonexistent Id</em>.
     */
    @Test
    public void checkGetNonExistentCategory() {
        getEntityById(Long.MAX_VALUE, Category.class, HttpStatus.NOT_FOUND);
    }

    /**
     * Check both {@link CategoryController#getCategoryById(long)} and
     * {@link CategoryController#addCategory(Category)} methods,
     * i.e. HTTP methods GET and POST, providing possibilities to get an {@link Category} entity
     * from REST resource and to add it to the resource.
     */
    @Test
    public void checkAddCategory() {

        //POST to REST
        Category createdCategory = getCreatedCategory();

        // GET from REST
        Category receivedCategory =
                getEntityById(
                        createdCategory.getId(),
                        Category.class,
                        HttpStatus.OK);

        assertEquals(createdCategory, receivedCategory);
    }

    /**
     * Check {@link CategoryController#getAllCategories()} method, i.e. HTTP method GET, used to
     * get a list of {@link Category} entities from REST resource
     * entities.<br>
     * <p>First of all, continuous addition of {@link Category} entities with amount equal to
     * {@link io.khasang.ba.controller.utility.RestRequests#TEST_ENTITIES_AMOUNT} is performed.
     * Secondly, top TEST_ENTITIES_AMOUNT of entities, obtained from response body, received from REST-resource, placed at
     * {@link io.khasang.ba.controller.utility.RestRequests#GET_ALL_PATH}), compared with list of
     * previously added entities.
     * </p>
     */
    @Test
    public void checkGetAllCategories() {

        // Create list of entities
        List<Category> createdCategorysList =
                getCreatedEntitiesList(
                        Category.class,
                        TEST_ENTITIES_AMOUNT,
                        HttpStatus.CREATED);

        // Receive all entities from REST
        List<Category> allCategorys =
                getAllEntitiesList(Category.class, HttpStatus.OK);

        // Check last TEST_ENTITIES_AMOUNT and assert for equality
        List<Category> receivedCategorysSubList =
                allCategorys.subList(allCategorys.size() - TEST_ENTITIES_AMOUNT,
                        allCategorys.size());

        assertEquals(createdCategorysList, receivedCategorysSubList);
    }

    /**
     * Check {@link CategoryController#updateCategory(Category)}, i.e. HTTP method
     * PUT, used to update an {@link Category} entity on REST resource
     */
    @Test
    public void checkUpdateCategory() {

        // POST, then UPDATE in REST
        Category updatedCategory = getUpdatedCategory();

        //Get it from REST, check id and assertEquals
        Category receivedCategory =
                getEntityById(
                        updatedCategory.getId(),
                        Category.class,
                        HttpStatus.OK);

        assertNotNull(receivedCategory.getId());
        assertEquals(updatedCategory, receivedCategory);
    }

    /**
     * Check {@link CategoryController#deleteCategory(long)}, i.e. HTTP method
     * DELETE, used to delete an {@link Category} entity on REST resource
     */
    @Test
    public void checkCategoryDelete() {
        Category createdCategory = getCreatedCategory();

        getResponseFromEntityDeleteRequest(
                createdCategory.getId(),
                Category.class,
                HttpStatus.NO_CONTENT);

        assertNull(getEntityById(
                createdCategory.getId(),
                Category.class,
                HttpStatus.NOT_FOUND));
    }

    //Addition constraints

    /**
     * Check unique constraint for <em>name</em> field while adding {@link Category}
     */
    @Test
    public void checkUniqueConstraintForName_whenCategoryRequestStageAdd() {
        addWithIncorrectField("name", getCreatedCategory().getName());
    }

    /**
     * Check not blank constraint for <em>name</em> while adding {@link Category}
     */
    @Test
    public void checkNotBlankConstraintForName_whenCategoryRequestStageAdd() {
        addWithIncorrectField("name", null);
        addWithIncorrectField("name", "");
        addWithIncorrectField("name", " ");
        addWithIncorrectField("name", "  ");
        addWithIncorrectField("name", "\t");
        addWithIncorrectField("name", "\n");
    }

    // Update constraints

    /**
     * Check unique constraint for <em>name</em> while updating {@link Category}
     */
    @Test
    public void checkUniqueConstraintForName_whenCategoryRequestStageUpdate() {
        updateWithIncorrectField("name", getCreatedCategory().getName());
    }

    /**
     * Check not blank constraint for <em>name</em> while updating {@link Category}
     */
    @Test
    public void checkNotBlankConstraintForName_whenCategoryRequestStageUpdate() {
        updateWithIncorrectField("name", null);
        updateWithIncorrectField("name", "");
        updateWithIncorrectField("name", " ");
        updateWithIncorrectField("name", "  ");
        updateWithIncorrectField("name", "\t");
        updateWithIncorrectField("name", "\n");
    }

    // Utility methods

    /**
     * Create mock {@link Category} instance, and add (i.e. POST) it to a REST resource
     *
     * @return added to REST resource entity
     */
    private Category getCreatedCategory() {
        Category Category = getMockCategory();

        Category createdCategory =
                getResponseFromEntityAddRequest(Category, HttpStatus.CREATED);

        assertNotNull(createdCategory.getId());
        Category.setId(createdCategory.getId());
        assertEquals(Category, createdCategory);

        return createdCategory;
    }

    /**
     * Update existing {@link Category} entity at REST resource. Firstly, add new mock entity and then
     * PUT updated entity to REST resource
     *
     * @return updated at REST resource instance of entity
     */
    private Category getUpdatedCategory() {
        Category createdCategory = getCreatedCategory();

        Category updatedCategory =
                getResponseFromEntityUpdateRequest(
                        getChangedMockCategory(createdCategory),
                        HttpStatus.OK);

        assertEquals(createdCategory.getId(), updatedCategory.getId());

        return updatedCategory;
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
        addEntityWithIncorrectField(Category.class, fieldName, incorrectValue, HttpStatus.INTERNAL_SERVER_ERROR);
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
        updateEntityWithIncorrectField(Category.class, fieldName, incorrectValue, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
