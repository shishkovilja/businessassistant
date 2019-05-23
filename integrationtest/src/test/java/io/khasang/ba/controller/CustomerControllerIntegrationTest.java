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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.khasang.ba.controller.utility.MockFactory.getMockCustomer;
import static io.khasang.ba.controller.utility.RestRequests.*;
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
}
