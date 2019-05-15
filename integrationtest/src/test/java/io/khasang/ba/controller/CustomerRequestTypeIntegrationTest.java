package io.khasang.ba.controller;

import io.khasang.ba.entity.CustomerRequestCategory;
import io.khasang.ba.entity.CustomerRequestType;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Integration test for CustomerRequestType REST layer
 */
public class CustomerRequestTypeIntegrationTest {

    //Mock data configuration
    private static final String TEST_CUSTOMER_REQUEST_TYPE_NAME = "TEST_CUSTOMER_REQUEST_TYPE_";
    private static final String TEST_CUSTOMER_DESCRIPTION = "Test Description of Customer's request type";

    //Amount of test entities
    private static final int TEST_ENTITIES_COUNT = 30;

    private static final String ROOT = "http://localhost:8080/customer_request_type";
    private static final String ADD = "/add";
    private static final String GET_BY_ID = "/get/{id}";
    private static final String GET_ALL = "/get/all";
    private static final String UPDATE = "/update";
    private static final String DELETE_BY_ID = "/delete/{id}";

    /**
     * Check customerRequestType addition
     */
    @Test
    public void checkAddCustomerRequestType() {
        CustomerRequestType createdCustomerRequestType = getCreatedCustomerRequestType();
        CustomerRequestType receivedCustomerRequestType = getCustomerRequestTypeById(createdCustomerRequestType.getId());
        assertNotNull(receivedCustomerRequestType);
        assertEquals(createdCustomerRequestType, receivedCustomerRequestType);
    }

    /**
     * Check unique name constraint for customer's request type name during addition process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkAddNameUniqueConstraint() {
        CustomerRequestType customerRequestType = getCreatedCustomerRequestType();
        CustomerRequestType customerRequestTypeWithSameName = getMockCustomerRequestType();
        customerRequestTypeWithSameName.setName(customerRequestType.getName());
        getResponseEntityFromPostRequest(customerRequestTypeWithSameName);
    }

    /**
     * Check NotBlank(+ NotNull + NotEmpty) constraint for customer's request type name during addition process
     */
    @Test
    public void checkAddWithBlankName() {

        //NotNull + NotEmpty
        addWithIncorrectField("name", null);
        addWithIncorrectField("name", "");

        //Check NotBlank (whitespaces and some other characters)
        addWithIncorrectField("name", "   ");
        addWithIncorrectField("name", "\t");
        addWithIncorrectField("name", "\n");
    }

    /**
     * Check NotNull constraint for customer's request type category during addition process
     */
    @Test
    public void checkAddWithNullCategory() {
        addWithIncorrectField("customerRequestCategory", null);
    }

    /**
     * Checks sequential addition of certain amount of customerRequestTypes addition and getting. Amount is set in
     * {@link #TEST_ENTITIES_COUNT} constant
     */
    @Test
    public void checkGetAllCustomerRequestTypes() {
        List<CustomerRequestType> createdCustomerRequestTypes = new ArrayList<>(TEST_ENTITIES_COUNT);
        for (int i = 0; i < TEST_ENTITIES_COUNT; i++) {
            createdCustomerRequestTypes.add(getCreatedCustomerRequestType());
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<CustomerRequestType>> responseEntity = restTemplate.exchange(
                ROOT + GET_ALL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CustomerRequestType>>() {
                }
        );
        List<CustomerRequestType> allReceivedCustomerRequestTypes = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(allReceivedCustomerRequestTypes);
        assertFalse(allReceivedCustomerRequestTypes.isEmpty());

        List<CustomerRequestType> receivedCustomerRequestTypesSubList =
                allReceivedCustomerRequestTypes.subList(allReceivedCustomerRequestTypes.size() - TEST_ENTITIES_COUNT,
                        allReceivedCustomerRequestTypes.size());
        for (int i = 0; i < TEST_ENTITIES_COUNT; i++) {
            assertEquals(createdCustomerRequestTypes.get(i), receivedCustomerRequestTypesSubList.get(i));
        }
    }

    /**
     * Check of customerRequestType entity update via PUT request
     */
    @Test
    public void checkUpdateCustomerRequestType() {
        CustomerRequestType customerRequestType = getChangedCustomerRequestType(getCreatedCustomerRequestType());
        putCustomerRequestTypeToUpdate(customerRequestType);

        CustomerRequestType updatedCustomerRequestType = getCustomerRequestTypeById(customerRequestType.getId());
        assertNotNull(updatedCustomerRequestType);
        assertNotNull(updatedCustomerRequestType.getId());
        assertEquals(customerRequestType, updatedCustomerRequestType);
    }

    /**
     * Check customer's request type name update constraint during updating process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkNameUpdate() {
        CustomerRequestType customerRequestType = getCreatedCustomerRequestType();
        customerRequestType.setName(TEST_CUSTOMER_REQUEST_TYPE_NAME + UUID.randomUUID().toString());
        putCustomerRequestTypeToUpdate(customerRequestType);
    }

    /**
     * Check NotNull constraint for customer's request type category during updating process
     */
    @Test
    public void checkUpdateWithNullCategory() {
        updateWithIncorrectField("customerRequestCategory", null);
    }

    /**
     * Check of customerRequestType deletion
     */
    @Test
    public void checkCustomerRequestTypeDelete() {
        CustomerRequestType customerRequestType = getCreatedCustomerRequestType();
        CustomerRequestType deletedCustomerRequestType = getDeletedCustomerRequestType(customerRequestType.getId());
        assertEquals(customerRequestType, deletedCustomerRequestType);
        assertNull(getCustomerRequestTypeById(customerRequestType.getId()));
    }

    /**
     * Utility method to check CustomerRequestType entity addition with incorrect field (overriding mock value).
     * Field is changed by Java reflections mechanisms. <br>
     * Method checks that thrown exception is {@link HttpServerErrorException} with <em>Internal Server Error</em>
     * status code in response. <br>
     * <em>NOTICE: This behaviour will be changed after REST layer response codes regulation</em>
     *
     * @param fieldName      name of the field to override
     * @param incorrectValue <em>incorrect value</em> used instead of mock value
     */
    private <T> void addWithIncorrectField(String fieldName, T incorrectValue) {
        try {
            CustomerRequestType mockCustomerRequestType = getMockCustomerRequestType();
            setField(mockCustomerRequestType, fieldName, incorrectValue);
            getResponseEntityFromPostRequest(mockCustomerRequestType);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail(e.toString());
        } catch (HttpServerErrorException e) {
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e.getStatusCode());
        }
    }

    /**
     * Utility method to check CustomerRequestType entity updating with incorrect field (overriding mock value).
     * Field is changed by Java reflections mechanisms. <br>
     * Method checks that thrown exception is {@link HttpServerErrorException} with <em>Internal Server Error</em>
     * status code in response. <br>
     * <em>NOTICE: This behaviour will be changed after REST layer response codes regulation</em>
     *
     * @param fieldName      name of the field to override
     * @param incorrectValue <em>incorrect value</em> used instead of mock value
     */
    private <T> void updateWithIncorrectField(String fieldName, T incorrectValue) {
        try {
            CustomerRequestType customerRequestType = getChangedCustomerRequestType(getCreatedCustomerRequestType());
            setField(customerRequestType, fieldName, incorrectValue);
            putCustomerRequestTypeToUpdate(customerRequestType);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail(e.toString());
        } catch (HttpServerErrorException e) {
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e.getStatusCode());
        }
    }

    /**
     * Utility method to set a CustomerRequestType's field via reflection mechanism
     *
     * @param customerRequestType CustomerRequestType instance to set field for
     * @param fieldName           name of field (eg. "login" or "email")
     * @param fieldValue          field value
     * @param <T>                 type parameter of field
     * @throws NoSuchFieldException   if field with specified fieldName not found
     * @throws IllegalAccessException in case of access errors
     */
    private <T> void setField(CustomerRequestType customerRequestType, String fieldName, T fieldValue)
            throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = CustomerRequestType.class.getDeclaredField(fieldName);
        declaredField.setAccessible(true);
        declaredField.set(customerRequestType, fieldValue);
        declaredField.setAccessible(false);
    }

    /**
     * Method for customerRequestType getting by id
     *
     * @param id Id in table of customerRequestTypes
     * @return Found {@link CustomerRequestType} instance
     */
    private CustomerRequestType getCustomerRequestTypeById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CustomerRequestType> responseEntity = restTemplate.exchange(
                ROOT + GET_BY_ID,
                HttpMethod.GET,
                null,
                CustomerRequestType.class,
                id
        );
        CustomerRequestType receivedCustomerRequestType = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        return receivedCustomerRequestType;
    }

    /**
     * Put customerRequestType for update
     *
     * @param customerRequestType CustomerRequestType, which should be updated on service
     */
    private void putCustomerRequestTypeToUpdate(CustomerRequestType customerRequestType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<CustomerRequestType> httpEntity = new HttpEntity<>(customerRequestType, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CustomerRequestType> responseEntity = restTemplate.exchange(
                ROOT + UPDATE,
                HttpMethod.PUT,
                httpEntity,
                CustomerRequestType.class
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    /**
     * Change users field for further update
     *
     * @param oldCustomerRequestType customerRequestType instance which updated {@link CustomerRequestType}
     * @return updated customerRequestType
     */
    private CustomerRequestType getChangedCustomerRequestType(CustomerRequestType oldCustomerRequestType) {
        CustomerRequestType newCustomerRequestType = new CustomerRequestType();

        newCustomerRequestType.setId(oldCustomerRequestType.getId());
        newCustomerRequestType.setName(oldCustomerRequestType.getName());
        newCustomerRequestType.setDescription("UPDATED description of customer request type");

        // Randomly select customer request category (0 -> HOME_SERVICE, 1 -> OFFICE_SERVICE)
        newCustomerRequestType.setCustomerRequestCategory(
                new Random().nextInt(2) == 0 ?
                        CustomerRequestCategory.HOME_SERVICE : CustomerRequestCategory.OFFICE_SERVICE
        );

        return newCustomerRequestType;
    }

    /**
     * Get created test CustomerRequestType entity from POST response during customerRequestType creation procedure. Instead of creating {@link CustomerRequestType}
     * instance by constructor, this method returns instance from response, thus created CustomerRequestType contains table identifier
     *
     * @return Instance of {@link CustomerRequestType} with generated identifier
     */
    private CustomerRequestType getCreatedCustomerRequestType() {
        CustomerRequestType customerRequestType = getMockCustomerRequestType();

        ResponseEntity<CustomerRequestType> responseEntity = getResponseEntityFromPostRequest(customerRequestType);
        CustomerRequestType createdCustomerRequestType = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(createdCustomerRequestType);
        assertNotNull(createdCustomerRequestType.getId());
        assertEquals(customerRequestType, createdCustomerRequestType);

        return createdCustomerRequestType;
    }

    /**
     * Create mock {@link CustomerRequestType} instance
     *
     * @return mock customerRequestType instance
     */
    private CustomerRequestType getMockCustomerRequestType() {
        CustomerRequestType customerRequestType = new CustomerRequestType();

        customerRequestType.setName(TEST_CUSTOMER_REQUEST_TYPE_NAME + UUID.randomUUID().toString());
        customerRequestType.setDescription(TEST_CUSTOMER_DESCRIPTION);

        // Randomly select customer request category (0 -> HOME_SERVICE, 1 -> OFFICE_SERVICE)
        customerRequestType.setCustomerRequestCategory(
                new Random().nextInt(2) == 0 ?
                        CustomerRequestCategory.HOME_SERVICE : CustomerRequestCategory.OFFICE_SERVICE
        );

        return customerRequestType;
    }

    /**
     * Add customerRequestType entity via POST request
     *
     * @param customerRequestType {@link CustomerRequestType} instance, which should be added via POST request
     * @return {@link ResponseEntity} containing response data
     */
    private ResponseEntity<CustomerRequestType> getResponseEntityFromPostRequest(CustomerRequestType customerRequestType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<CustomerRequestType> httpEntity = new HttpEntity<>(customerRequestType, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                ROOT + ADD,
                HttpMethod.POST,
                httpEntity,
                CustomerRequestType.class
        );
    }

    /**
     * Utility method which deletes customerRequestType by id and retrieves customerRequestType entity from DELETE response body
     *
     * @param id Id of the customerRequestType which should be deleted
     * @return Deleted customerRequestType
     */
    private CustomerRequestType getDeletedCustomerRequestType(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CustomerRequestType> responseEntity = restTemplate.exchange(
                ROOT + DELETE_BY_ID,
                HttpMethod.DELETE,
                null,
                CustomerRequestType.class,
                id
        );
        CustomerRequestType deletedCustomerRequestType = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(deletedCustomerRequestType);
        return deletedCustomerRequestType;
    }
}
