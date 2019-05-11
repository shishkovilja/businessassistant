package io.khasang.ba.controller;

import io.khasang.ba.entity.Customer;
import io.khasang.ba.entity.CustomerInformation;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Integration test for Customer REST layer
 */
public class CustomerControllerIntegrationTest {
    //Mock data configuration
    private static final String TEST_CUSTOMER_LOGIN_PREFIX = "TEST_CUSTOMER_";
    private static final String TEST_CUSTOMER_RAW_PASSWORD = "123tEsT#";
    private static final String TEST_CUSTOMER_EMAIL_SUFFIX = "@ba.khasang.io";
    private static final String TEST_CUSTOMER_FULL_NAME = "Ivan Petrov";
    private static final LocalDate TEST_CUSTOMER_BIRTHDATE = LocalDate.of(1986, 8, 26);
    private static final String TEST_CUSTOMER_COUNTRY = "Russia";
    private static final String TEST_CUSTOMER_CITY = "Saint Petersburg";
    private static final String TEST_CUSTOMER_ABOUT = "Another one mock test customer";

    //Amount of test entities
    private static final int TEST_ENTITIES_COUNT = 30;

    private static final String ROOT = "http://localhost:8080/customer";
    private static final String ADD = "/add";
    private static final String GET_BY_ID = "/get/{id}";
    private static final String GET_ALL = "/get/all";
    private static final String UPDATE = "/update";
    private static final String DELETE_BY_ID = "/delete/{id}";

    /**
     * Check customer addition
     */
    @Test
    public void checkAddCustomer() {
        Customer createdCustomer = getCreatedCustomer();
        Customer receivedCustomer = getCustomerById(createdCustomer.getId());
        assertNotNull(receivedCustomer);
        assertEquals(createdCustomer, receivedCustomer);
    }

    /**
     * Check unique login constraint for customer's login during addition process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkAddLoginUniqueConstraint() {
        Customer customer = getCreatedCustomer();
        Customer customerWithSameLogin = getMockCustomer();
        customerWithSameLogin.setLogin(customer.getLogin());
        getResponseEntityFromPostRequest(customerWithSameLogin);
    }

    /**
     * Check unique constraint for customer's e-mail during addition process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkAddEmailUniqueConstraint() {
        Customer customer = getCreatedCustomer();
        Customer customerWithSameEmail = getMockCustomer();
        customerWithSameEmail.setEmail(customer.getEmail());
        getResponseEntityFromPostRequest(customerWithSameEmail);
    }

    /**
     * Check NotBlank(+ NotNull + NotEmpty) constraint for customer's login during addition process
     */
    @Test
    public void checkAddWithBlankLogin() {

        //NotNull + NotEmpty
        addWithIncorrectField("login", null);
        addWithIncorrectField("login", "");

        //Check NotBlank (whitespaces and some other characters)
        addWithIncorrectField("login", "   ");
        addWithIncorrectField("login", "\t");
        addWithIncorrectField("login", "\n");
    }

    /**
     * Check NotBlank(+ NotNull + NotEmpty) constraint for customer's email during addition process
     */
    @Test
    public void checkAddWithBlankEmail() {

        //NotNull + NotEmpty
        addWithIncorrectField("email", null);
        addWithIncorrectField("email", "");

        //Check NotBlank (whitespaces and some other characters)
        addWithIncorrectField("email", "   ");
        addWithIncorrectField("email", "\t");
        addWithIncorrectField("email", "\n");
    }

    /**
     * Check NotBlank(+ NotNull + NotEmpty) constraint for customer's password during addition process
     */
    @Test
    public void checkAddWithBlankPassword() {

        //NotNull + NotEmpty
        addWithIncorrectField("password", null);
        addWithIncorrectField("password", "");

        //Check NotBlank (whitespaces and some other characters)
        addWithIncorrectField("password", "   ");
        addWithIncorrectField("password", "\t");
        addWithIncorrectField("password", "\n");
    }

    /**
     * Checks sequential addition of certain amount of customers addition and getting. Amount is set in
     * {@link #TEST_ENTITIES_COUNT} constant
     */
    @Test
    public void checkGetAllCustomers() {
        List<Customer> createdCustomers = new ArrayList<>(TEST_ENTITIES_COUNT);
        for (int i = 0; i < TEST_ENTITIES_COUNT; i++) {
            createdCustomers.add(getCreatedCustomer());
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(
                ROOT + GET_ALL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Customer>>() {
                }
        );
        List<Customer> allReceivedCustomers = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(allReceivedCustomers);
        assertFalse(allReceivedCustomers.isEmpty());

        List<Customer> receivedCustomersSubList =
                allReceivedCustomers.subList(allReceivedCustomers.size() - TEST_ENTITIES_COUNT, allReceivedCustomers.size());
        for (int i = 0; i < TEST_ENTITIES_COUNT; i++) {
            assertEquals(createdCustomers.get(i), receivedCustomersSubList.get(i));
        }
    }

    /**
     * Check of customer entity update via PUT request
     */
    @Test
    public void checkUpdateCustomer() {
        Customer customer = getChangedCustomer(getCreatedCustomer());
        putCustomerToUpdate(customer);

        Customer updatedCustomer = getCustomerById(customer.getId());
        assertNotNull(updatedCustomer);
        assertNotNull(updatedCustomer.getId());
        assertEquals(customer, updatedCustomer);
    }

    /**
     * Check customer's login update constraint during updating process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkLoginUpdate() {
        Customer customer = getCreatedCustomer();
        customer.setLogin(TEST_CUSTOMER_LOGIN_PREFIX + UUID.randomUUID().toString());
        putCustomerToUpdate(customer);
    }

    /**
     * Check unique constraint for customer's e-mail during updating process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkUpdateEmailUniqueConstraint() {
        Customer customer1 = getCreatedCustomer();
        Customer customer2 = getCreatedCustomer();
        customer2.setEmail(customer1.getEmail());
        putCustomerToUpdate(customer2);
    }

    /**
     * Check NotBlank(+ NotNull + NotEmpty) constraint for customer's login during updating process
     */
    @Test
    public void checkUpdateWithBlankLogin() {

        //NotNull + NotEmpty
        updateWithIncorrectField("login", null);
        updateWithIncorrectField("login", "");

        //Check NotBlank (whitespaces and some other characters)
        updateWithIncorrectField("login", "   ");
        updateWithIncorrectField("login", "\t");
        updateWithIncorrectField("login", "\n");
    }

    /**
     * Check NotBlank(+ NotNull + NotEmpty) constraint for customer's email during updating process
     */
    @Test
    public void checkUpdateWithBlankEmail() {

        //NotNull + NotEmpty
        updateWithIncorrectField("email", null);
        updateWithIncorrectField("email", "");

        //Check NotBlank (whitespaces and some other characters)
        updateWithIncorrectField("email", "   ");
        updateWithIncorrectField("email", "\t");
        updateWithIncorrectField("email", "\n");
    }

    /**
     * Check NotBlank(+ NotNull + NotEmpty) constraint for customer's password during updating process
     */
    @Test
    public void checkUpdateWithBlankPassword() {

        //NotNull + NotEmpty
        updateWithIncorrectField("password", null);
        updateWithIncorrectField("password", "");

        //Check NotBlank (whitespaces and some other characters)
        updateWithIncorrectField("password", "   ");
        updateWithIncorrectField("password", "\t");
        updateWithIncorrectField("password", "\n");
    }

    /**
     * Check of customer deletion
     */
    @Test
    public void checkCustomerDelete() {
        Customer customer = getCreatedCustomer();
        Customer deletedCustomer = getDeletedCustomer(customer.getId());
        assertEquals(customer, deletedCustomer);
        assertNull(getCustomerById(customer.getId()));
    }

    /**
     * Utility method to check Customer entity addition with incorrect field (overriding mock value).
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
            Customer mockCustomer = getMockCustomer();
            setField(mockCustomer, fieldName, incorrectValue);
            getResponseEntityFromPostRequest(mockCustomer);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail(e.toString());
        } catch (HttpServerErrorException e) {
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e.getStatusCode());
        }
    }

    /**
     * Utility method to check Customer entity updating with incorrect field (overriding mock value).
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
            Customer customer = getChangedCustomer(getCreatedCustomer());
            setField(customer, fieldName, incorrectValue);
            putCustomerToUpdate(customer);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail(e.toString());
        } catch (HttpServerErrorException e) {
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e.getStatusCode());
        }
    }

    /**
     * Utility method to set a Customer's field via reflection mechanism
     *
     * @param customer   Customer instance to set field for
     * @param fieldName  name of field (eg. "login" or "email")
     * @param fieldValue field value
     * @param <T>        type parameter of field
     * @throws NoSuchFieldException   if field with specified fieldName not found
     * @throws IllegalAccessException in case of access errors
     */
    private <T> void setField(Customer customer, String fieldName, T fieldValue)
            throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = Customer.class.getDeclaredField(fieldName);
        declaredField.setAccessible(true);
        declaredField.set(customer, fieldValue);
        declaredField.setAccessible(false);
    }

    /**
     * Method for customer getting by id
     *
     * @param id Id in table of customers
     * @return Found {@link Customer} instance
     */
    private Customer getCustomerById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Customer> responseEntity = restTemplate.exchange(
                ROOT + GET_BY_ID,
                HttpMethod.GET,
                null,
                Customer.class,
                id
        );
        Customer receivedCustomer = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        return receivedCustomer;
    }

    /**
     * Put customer for update
     *
     * @param customer Customer, which should be updated on service
     */
    private void putCustomerToUpdate(Customer customer) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Customer> httpEntity = new HttpEntity<>(customer, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Customer> responseEntity = restTemplate.exchange(
                ROOT + UPDATE,
                HttpMethod.PUT,
                httpEntity,
                Customer.class
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    /**
     * Change users field for further update
     *
     * @param oldCustomer customer instance which updated {@link Customer}
     * @return updated customer
     */
    private Customer getChangedCustomer(Customer oldCustomer) {
        Customer newCustomer = new Customer();
        CustomerInformation customerInformation = new CustomerInformation();

        newCustomer.setId(oldCustomer.getId());
        newCustomer.setLogin(oldCustomer.getLogin());
        newCustomer.setPassword("new_password");
        newCustomer.setEmail(UUID.randomUUID().toString() + "@newmail.com");
        newCustomer.setCustomerInformation(customerInformation);

        customerInformation.setFullName("New Full Name");
        customerInformation.setBirthDate(LocalDate.of(1990, 10, 15));
        customerInformation.setCountry("USA");
        customerInformation.setCity("New York");
        customerInformation.setAbout("new_about");

        return newCustomer;
    }

    /**
     * Get created test customer entity from POST response during customer creation procedure. Instead of creating {@link Customer}
     * instance by constructor, this method returns instance from response, thus created customer contains table identifier
     *
     * @return Instance of {@link Customer} with generated identifier
     */
    private Customer getCreatedCustomer() {
        Customer customer = getMockCustomer();

        LocalDateTime timeBeforeCreation = LocalDateTime.now();
        ResponseEntity<Customer> responseEntity = getResponseEntityFromPostRequest(customer);
        Customer createdCustomer = responseEntity.getBody();
        LocalDateTime timeAfterCreation = LocalDateTime.now();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(createdCustomer);
        assertNotNull(createdCustomer.getId());
        assertEquals(customer, createdCustomer);
        assertEquals(-1, timeBeforeCreation.compareTo(createdCustomer.getRegistrationTimestamp()));
        assertEquals(1, timeAfterCreation.compareTo(createdCustomer.getRegistrationTimestamp()));
        return createdCustomer;
    }

    /**
     * Create mock {@link Customer} instance
     *
     * @return mock customer instance
     */
    private Customer getMockCustomer() {
        Customer customer = new Customer();
        CustomerInformation customerInformation = new CustomerInformation();

        customer.setLogin(TEST_CUSTOMER_LOGIN_PREFIX + UUID.randomUUID().toString());
        customer.setPassword(TEST_CUSTOMER_RAW_PASSWORD);
        customer.setEmail(UUID.randomUUID().toString() + TEST_CUSTOMER_EMAIL_SUFFIX);
        customer.setCustomerInformation(customerInformation);

        customerInformation.setFullName(TEST_CUSTOMER_FULL_NAME);
        customerInformation.setBirthDate(TEST_CUSTOMER_BIRTHDATE);
        customerInformation.setCountry(TEST_CUSTOMER_COUNTRY);
        customerInformation.setCity(TEST_CUSTOMER_CITY);
        customerInformation.setAbout(TEST_CUSTOMER_ABOUT);

        return customer;
    }

    /**
     * Add customer entity via POST request
     *
     * @param customer {@link Customer} instance, which should be added via POST request
     * @return {@link ResponseEntity} containing response data
     */
    private ResponseEntity<Customer> getResponseEntityFromPostRequest(Customer customer) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Customer> httpEntity = new HttpEntity<>(customer, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                ROOT + ADD,
                HttpMethod.POST,
                httpEntity,
                Customer.class
        );
    }

    /**
     * Utility method which deletes customer by id and retrieves customer entity from DELETE response body
     *
     * @param id Id of the customer which should be deleted
     * @return Deleted customer
     */
    private Customer getDeletedCustomer(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Customer> responseEntity = restTemplate.exchange(
                ROOT + DELETE_BY_ID,
                HttpMethod.DELETE,
                null,
                Customer.class,
                id
        );
        Customer deletedCustomer = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(deletedCustomer);
        return deletedCustomer;
    }
}
