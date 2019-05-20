package io.khasang.ba.controller;

import io.khasang.ba.entity.Category;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class CategoryControllerIntegrationTest {
    private final String LAN_ADDRESS = "localhost";
    private final String PORT = "8080";
    private final String ROOT = "http://" + LAN_ADDRESS + ":" + PORT + "/category";
    private final String ADD = "/add";
    private final String GET = "/get";
    private final String ID = "/{id}";
    private final String ALL = "/all";
    private final String DELETE = "/delete";
    private final String UPDATE = "/update";


    /**
     * Test correct delete record into DB
     */
    @Test
    public void deleteCategory() {
        Category category;
        ResponseEntity<Category> responseEntity;
        category = prefillCategory();
        category = createCategory(category);
        responseEntity = deleteCategoryById(category.getId());

        // Check response is OK
        Assert.assertEquals("Request is bad. Must be OK", "OK", responseEntity.getStatusCode().getReasonPhrase());

        // Record must doesn't find into DB
        Assert.assertNull("Entity doesn't delete into DB", getCategoryById(category.getId()));
    }

    /**
     * Test record entity
     * Requset must be record. Data don't count
     */
    @Test
    public void addCategory() {
        Category category = prefillCategory();
        Category responseBodyCategory = createCategory(category);

        // Response create not null
        Assert.assertNotNull("Response must be Entity", responseBodyCategory);
        deleteCategoryById(responseBodyCategory.getId());
    }

    /**
     * Read entity by Id
     * @param id - id Entity
     * @return entity
     */
    private Category getCategoryById(long id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Category> responseEntity = restTemplate.exchange(
                ROOT + GET + ID,
                HttpMethod.GET,
                null,
                Category.class,
                id
        );
        Assert.assertEquals("Request is bad. Must be OK", "OK", responseEntity.getStatusCode().getReasonPhrase());

        return responseEntity.getBody();
    }

    /**
     * Add new Address into DB
     * Test equal source Address with added
     */
    @Test
    public void addCategoryWithEqualContent() {
        Category category;
        Category responseBodyCategory;
        category = prefillCategory();

        responseBodyCategory = createCategory(category);

        // Equals two entity created and template
        Assert.assertTrue("Fields don't equals. Must be equals", equals(category, responseBodyCategory));
        deleteCategoryById(responseBodyCategory.getId());
    }

    /**
     * Entity must update something self fields
     */
    @Test
    public void updateCategory() {
        Category category;
        Category responseCreatedCategory;
        Category responseUpdateCategory;

        category = prefillCategory();
        responseCreatedCategory = createCategory(category);

        // Change data
        category.setName("name2");
        category.setId(responseCreatedCategory.getId());
        responseUpdateCategory = updateCategory(category);

        // Created address mustn't be equals updated address
        Assert.assertFalse("Fields are equals. They must be don't equals", equals(responseCreatedCategory, responseUpdateCategory));

        // Delete entity into DB
        deleteCategoryById(responseUpdateCategory.getId());
    }

    @Test
    public void getAllCategories() {
        List<Category> categories;

        categories = new ArrayList<>();
        categories.add(createCategory(prefillCategory()));
        categories.add(createCategory(prefillCategory()));
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Category>> responseEntity = restTemplate.exchange(
                ROOT + GET + ALL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Category>>() {
                }
        );
        Assert.assertEquals("Request is bad. Must be OK", "OK", responseEntity.getStatusCode().getReasonPhrase());
        Assert.assertNotNull("Not all addresses were recieve. Must be more", categories.get(0));
        Assert.assertNotNull("Not all addresses were recieve. Must be more", categories.get(1));
        deleteCategoryById(categories.get(0).getId());
        deleteCategoryById(categories.get(1).getId());
    }

    /**
     * Create record into DB
     * @param entity create entity
     * @return responseBody
     */
    private Category createCategory(Category entity) {
        RestTemplate restTemplate;
        HttpHeaders httpHeaders;
        HttpEntity<Category> httpEntity;
        Category createdCategory;

        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpEntity = new HttpEntity<>(entity, httpHeaders);

        createdCategory = restTemplate.exchange(
                ROOT + ADD,
                HttpMethod.POST,
                httpEntity,
                Category.class
        ).getBody();

        return createdCategory;
    }

    /**
     * Update record into DB
     * @param entity update entity
     * @return response body
     */
    private Category updateCategory(Category entity) {
        RestTemplate restTemplate;
        HttpHeaders httpHeaders;
        HttpEntity<Category> httpEntity;
        Category updateCategory;

        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpEntity = new HttpEntity<>(entity, httpHeaders);

        updateCategory = restTemplate.exchange(
                ROOT + UPDATE,
                HttpMethod.PUT,
                httpEntity,
                Category.class
        ).getBody();

        return  updateCategory;
    }

    /**
     * Delete entity into DB by Id
     * @param id - id entity
     * @return response action
     */
    private ResponseEntity<Category> deleteCategoryById(long id) {
        RestTemplate restTemplate;
        restTemplate = new RestTemplate();

        return restTemplate.exchange(
                ROOT + DELETE + ID,
                HttpMethod.DELETE,
                null,
                Category.class,
                id
        );
    }

    /**
     * Create and fill Category
     * @return created Category
     */
    private Category prefillCategory() {
        return new Category("Hospital");
    }

    /**
     * Compare Category entity
     * @param source - source entity
     * @param target - equals entity
     * @return is equals?
     */
    private boolean equals(Category source, Category target) {
        boolean isCheck = false;

        if ( source.getName().equals(target.getName())) {
            isCheck = true;
        }

        return isCheck;
    }
}
