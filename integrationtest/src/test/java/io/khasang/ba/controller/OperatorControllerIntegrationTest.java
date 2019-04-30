package io.khasang.ba.controller;

import io.khasang.ba.entity.Operator;
import io.khasang.ba.entity.OperatorInformation;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Integration test for Operator REST layer
 */
public class OperatorControllerIntegrationTest {
    //Mock data configuration
    private static final String TEST_OPERATOR_LOGIN_PREFIX = "TEST_OPERATOR_";
    private static final String TEST_OPERATOR_RAW_PASSWORD = "123tEsT#";
    private static final String TEST_OPERATOR_EMAIL_SUFFIX = "@ba.khasang.io";
    private static final String TEST_OPERATOR_FULL_NAME = "Ivan Petrov";
    private static final LocalDate TEST_OPERATOR_BIRTHDATE = LocalDate.of(1986, 8, 26);
    private static final String TEST_OPERATOR_COUNTRY = "Russia";
    private static final String TEST_OPERATOR_CITY = "Saint Petersburg";
    private static final String TEST_OPERATOR_ABOUT = "Another one mock test operator";

    //Amount of test entities
    private static final int TEST_ENTITIES_COUNT = 30;

    private static final String ROOT = "http://localhost:8080/operator";
    private static final String ADD = "/add";
    private static final String GET_BY_ID = "/get/{id}";
    private static final String GET_ALL = "/get/all";
    private static final String UPDATE = "/update";
    private static final String DELETE_BY_ID = "/delete/{id}";

    /**
     * Check operator addition
     */
    @Test
    public void checkAddOperator() {
        Operator createdOperator = getCreatedOperator();
        Operator receivedOperator = getOperatorById(createdOperator.getId());
        assertNotNull(receivedOperator);
        assertEquals(createdOperator, receivedOperator);
    }

    /**
     * Check unique login constraint for operator's login during addition process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkAddLoginUniqueConstraint() {
        Operator operator = getCreatedOperator();
        Operator operatorWithSameLogin = getMockOperator();
        operatorWithSameLogin.setLogin(operator.getLogin());
        getResponseEntityFromPostRequest(operatorWithSameLogin);
    }

    /**
     * Check unique constraint for operator's e-mail during addition process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkAddEmailUniqueConstraint() {
        Operator operator = getCreatedOperator();
        Operator operatorWithSameEmail = getMockOperator();
        operatorWithSameEmail.setEmail(operator.getEmail());
        getResponseEntityFromPostRequest(operatorWithSameEmail);
    }

    /**
     * Check not-null constraint for operator's login during addition process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkAddWithNullLogin() {
        Operator operatorWithNullLogin = getMockOperator();
        operatorWithNullLogin.setLogin(null);
        getResponseEntityFromPostRequest(operatorWithNullLogin);
    }

    /**
     * Check not-empty constraint for operator's login during addition process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkAddWithEmptyLogin() {
        Operator operatorWithEmptyLogin = getMockOperator();
        operatorWithEmptyLogin.setLogin("");
        getResponseEntityFromPostRequest(operatorWithEmptyLogin);
    }

    /**
     * Check not-null constraint for operator's email during addition process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkAddWithNullEmail() {
        Operator operatorWithNullEmail = getMockOperator();
        operatorWithNullEmail.setEmail(null);
        getResponseEntityFromPostRequest(operatorWithNullEmail);
    }

    /**
     * Check not-empty constraint for operator's email during addition process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkAddWithEmptyEmail() {
        Operator operatorWithEmptyEmail = getMockOperator();
        operatorWithEmptyEmail.setEmail("");
        getResponseEntityFromPostRequest(operatorWithEmptyEmail);
    }

    /**
     * Check not-null constraint for operator's password during addition process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkAddWithNullPassword() {
        Operator operatorWithNullPassword = getMockOperator();
        operatorWithNullPassword.setPassword(null);
        getResponseEntityFromPostRequest(operatorWithNullPassword);
    }

    /**
     * Check not-empty constraint for operator's password during addition process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkAddWithEmptyPassword() {
        Operator operatorWithEmptyPassword = getMockOperator();
        operatorWithEmptyPassword.setPassword("");
        getResponseEntityFromPostRequest(operatorWithEmptyPassword);
    }

    /**
     * Checks sequential addition of certain amount of operators addition and getting. Amount is set in
     * {@link #TEST_ENTITIES_COUNT} constant
     */
    @Test
    public void checkGetAllOperators() {
        List<Operator> createdOperators = new ArrayList<>(TEST_ENTITIES_COUNT);
        for (int i = 0; i < TEST_ENTITIES_COUNT; i++) {
            createdOperators.add(getCreatedOperator());
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Operator>> responseEntity = restTemplate.exchange(
                ROOT + GET_ALL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Operator>>() {
                }
        );
        List<Operator> allReceivedOperators = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(allReceivedOperators);
        assertFalse(allReceivedOperators.isEmpty());

        List<Operator> receivedOperatorsSubList =
                allReceivedOperators.subList(allReceivedOperators.size() - TEST_ENTITIES_COUNT, allReceivedOperators.size());
        for (int i = 0; i < TEST_ENTITIES_COUNT; i++) {
            assertEquals(createdOperators.get(i), receivedOperatorsSubList.get(i));
        }
    }

    /**
     * Check of operator entity update via PUT request
     */
    @Test
    public void checkUpdateOperator() {
        Operator operator = getChangedOperator(getCreatedOperator());
        putOperatorToUpdate(operator);

        Operator updatedOperator = getOperatorById(operator.getId());
        assertNotNull(updatedOperator);
        assertNotNull(updatedOperator.getId());
        assertEquals(operator, updatedOperator);
    }

    /**
     * Check operator's login update constraint during updating process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkLoginUpdate() {
        Operator operator = getCreatedOperator();
        operator.setLogin(TEST_OPERATOR_LOGIN_PREFIX + UUID.randomUUID().toString());
        putOperatorToUpdate(operator);
    }

    /**
     * Check unique constraint for operator's e-mail during updating process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkUpdateEmailUniqueConstraint() {
        Operator operator1 = getCreatedOperator();
        Operator operator2 = getCreatedOperator();
        operator2.setEmail(operator1.getEmail());
        putOperatorToUpdate(operator2);
    }

    /**
     * Check not-null constraint for operator's email during updating process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkUpdateWithNullEmail() {
        Operator operator = getCreatedOperator();
        operator.setEmail(null);
        putOperatorToUpdate(operator);
    }

    /**
     * Check not-empty constraint for operator's email during updating process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkUpdateWithEmptyEmail() {
        Operator operator = getCreatedOperator();
        operator.setEmail("");
        putOperatorToUpdate(operator);
    }

    /**
     * Check not-null constraint for operator's password during updating process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkUpdateWithNullPassword() {
        Operator operator = getCreatedOperator();
        operator.setPassword(null);
        putOperatorToUpdate(operator);
    }

    /**
     * Check not-empty constraint for operator's password during updating process
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkUpdateWithEmptyPassword() {
        Operator operator = getCreatedOperator();
        operator.setPassword("");
        putOperatorToUpdate(operator);
    }

    /**
     * Check of operator deletion
     */
    @Test
    public void checkOperatorDelete() {
        Operator operator = getCreatedOperator();
        Operator deletedOperator = getDeletedOperator(operator.getId());
        assertEquals(operator, deletedOperator);
        assertNull(getOperatorById(operator.getId()));
    }

    /**
     * Utility method which deletes operator by id and retrieves operator entity from DELETE response body
     *
     * @param id Id of the operator which should be deleted
     * @return Deleted operator
     */
    private Operator getDeletedOperator(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Operator> responseEntity = restTemplate.exchange(
                ROOT + DELETE_BY_ID,
                HttpMethod.DELETE,
                null,
                Operator.class,
                id
        );
        Operator deletedOperator = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(deletedOperator);
        return deletedOperator;
    }

    /**
     * Method for operator getting by id
     *
     * @param id Id in table of operators
     * @return Found {@link Operator} instance
     */
    private Operator getOperatorById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Operator> responseEntity = restTemplate.exchange(
                ROOT + GET_BY_ID,
                HttpMethod.GET,
                null,
                Operator.class,
                id
        );
        Operator receivedOperator = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        return receivedOperator;
    }

    /**
     * Put operator for update
     *
     * @param operator Operator, which should be updated on service
     */
    private void putOperatorToUpdate(Operator operator) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Operator> httpEntity = new HttpEntity<>(operator, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Operator> responseEntity = restTemplate.exchange(
                ROOT + UPDATE,
                HttpMethod.PUT,
                httpEntity,
                Operator.class
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    /**
     * Change operators field for further update
     *
     * @param oldOperator operator instance which updated {@link Operator}
     * @return updated operator
     */
    private Operator getChangedOperator(Operator oldOperator) {
        OperatorBuilder operatorBuilder = new OperatorBuilder();
        Operator newOperator = operatorBuilder
                .addLogin(oldOperator.getLogin())
                .addEmail(UUID.randomUUID().toString() + "@newmail.com")
                .addPassword("new_password")
                .addFullName("New Full Name")
                .addBirthDate(LocalDate.of(1990, 10, 15))
                .addCountry("USA")
                .addCity("New York")
                .addAbout("new_about")
                .build();
        newOperator.setId(oldOperator.getId());
        return newOperator;
    }

    /**
     * Get created test operator entity from POST response during operator creation procedure. Instead of creating {@link Operator}
     * instance by constructor, this method returns instance from response, thus created operator contains table identifier
     *
     * @return Instance of {@link Operator} with generated identifier
     */
    private Operator getCreatedOperator() {
        Operator operator = getMockOperator();

        LocalDateTime timeBeforeCreation = LocalDateTime.now();
        ResponseEntity<Operator> responseEntity = getResponseEntityFromPostRequest(operator);
        Operator createdOperator = responseEntity.getBody();
        LocalDateTime timeAfterCreation = LocalDateTime.now();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(createdOperator);
        assertNotNull(createdOperator.getId());
        assertEquals(operator, createdOperator);
        assertEquals(-1, timeBeforeCreation.compareTo(createdOperator.getRegistrationTimestamp()));
        assertEquals(1, timeAfterCreation.compareTo(createdOperator.getRegistrationTimestamp()));
        return createdOperator;
    }

    /**
     * Build mock {@link Operator} instance vie {@link OperatorBuilder}
     *
     * @return prefilled mock operator instance
     */
    private Operator getMockOperator() {
        OperatorBuilder operatorBuilder = new OperatorBuilder();
        return operatorBuilder
                .addLogin(TEST_OPERATOR_LOGIN_PREFIX + UUID.randomUUID().toString())
                .addEmail(UUID.randomUUID().toString() + TEST_OPERATOR_EMAIL_SUFFIX)
                .addAbout(TEST_OPERATOR_ABOUT)
                .addCity(TEST_OPERATOR_CITY)
                .addFullName(TEST_OPERATOR_FULL_NAME)
                .addPassword(TEST_OPERATOR_RAW_PASSWORD)
                .addBirthDate(TEST_OPERATOR_BIRTHDATE)
                .addCountry(TEST_OPERATOR_COUNTRY)
                .build();
    }

    /**
     * Add operator entity via POST request
     *
     * @param operator {@link Operator} instance, which should be added via POST request
     * @return {@link ResponseEntity} containing response data
     */
    private ResponseEntity<Operator> getResponseEntityFromPostRequest(Operator operator) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Operator> httpEntity = new HttpEntity<>(operator, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                ROOT + ADD,
                HttpMethod.POST,
                httpEntity,
                Operator.class
        );
    }

    /**
     * Static inner builder of operator
     */
    protected static class OperatorBuilder {
        private OperatorInformation operatorInformation;

        //Instance of buildabe Operator
        private Operator operator;

        public OperatorBuilder() {
            operator = new Operator();
            operatorInformation = new OperatorInformation();
            operator.setOperatorInformation(operatorInformation);
        }

        public Operator build() {
            assertNotNull(operator.getLogin());
            assertNotNull(operator.getPassword());
            assertNotNull(operator.getEmail());
            return operator;
        }

        public OperatorBuilder addLogin(String login) {
            operator.setLogin(login);
            return this;
        }

        public OperatorBuilder addEmail(String email) {
            operator.setEmail(email);
            return this;
        }

        public OperatorBuilder addPassword(String password) {
            operator.setPassword(password);
            return this;
        }

        public OperatorBuilder addBirthDate(LocalDate birthDate) {
            operatorInformation.setBirthDate(birthDate);
            return this;
        }

        public OperatorBuilder addFullName(String fullName) {
            operatorInformation.setFullName(fullName);
            return this;
        }

        public OperatorBuilder addAbout(String about) {
            operatorInformation.setAbout(about);
            return this;
        }

        public OperatorBuilder addCountry(String country) {
            operatorInformation.setCountry(country);
            return this;
        }

        public OperatorBuilder addCity(String city) {
            operatorInformation.setCity(city);
            return this;
        }
    }
}
