package io.khasang.ba.controller.utility;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * This class providing utility methods for HTTP requests to REST resources in order to reduce duplicating of the code.
 */
public final class RestRequests {

    /**
     * Amount of test entities
     */
    public static final int TEST_ENTITIES_AMOUNT = 10;

    /**
     * Main REST service address
     */
    public static String REST_ROOT = "http://localhost:8080/";

    // Roots of REST resources
    public static final String CUSTOMER_REQUEST_STAGE_ROOT = REST_ROOT + "customer_request_stage";
    public static final String OPERATOR_ROOT = REST_ROOT + "operator";

    // Common addresses of REST resources
    public static final String ADD_PATH = "/add";
    public static final String GET_BY_ID_PATH = "/get/{id}";
    public static final String GET_ALL_PATH = "/get/all";
    public static final String UPDATE_PATH = "/update";
    public static final String DELETE_BY_ID_PATH = "/delete/{id}";

    /**
     * Add an entity via POST HTTP-method, check status codes of response, provide necessary assertions,
     * and return body of the response
     *
     * @param entity             the entity, which should be added
     * @param postUrl            an URL of receiving POST request REST-resource
     * @param expectedStatusCode expected HTTP status code of response
     * @param <T>                type of the entity <em>both of request and response</em>
     * @return POST response body
     */
    public static <T> T getResponseFromEntityAddRequest(T entity, String postUrl, HttpStatus expectedStatusCode) {
        return getBodyOfResponseToSendEntityRequest(entity, postUrl, HttpMethod.POST, expectedStatusCode);
    }

    /**
     * Get an entity via GET HTTP-method, check status codes of response, provide necessary assertions,
     * and return body of the response
     *
     * @param id                 of an entity which should be found
     * @param entityClass        class of the desired entity
     * @param getUrl             an URL of receiving GET request REST-resource
     * @param expectedStatusCode expected HTTP status code of response
     * @param <T>                type of the entity returned in response body
     * @return GET response body, i.e. entity instance
     */
    public static <T> T getEntityById(Long id, Class<T> entityClass, String getUrl, HttpStatus expectedStatusCode) {
        return tryToGetResponseBody(() -> {
                    ResponseEntity<T> responseEntity = new RestTemplate().exchange(
                            getUrl,
                            HttpMethod.GET,
                            null,
                            entityClass,
                            id);

                    assertEquals(expectedStatusCode, responseEntity.getStatusCode());
                    T entity1 = responseEntity.getBody();
                    assertNotNull(entity1);

                    return entity1;
                },
                expectedStatusCode);
    }

    /**
     * Get all entities via GET HTTP-method, check status codes of response, provide necessary assertions,
     * and return body of the response
     *
     * @param <T>                type of entities list returned in response body
     * @param getAllUrl          an URL of receiving GET request REST-resource
     * @param expectedStatusCode expected HTTP status code of response
     * @return list of all entities from the given resource
     */
    public static <T> List<T> getAllEntitiesList(ParameterizedTypeReference<List<T>> typeReference, String getAllUrl,
                                                 HttpStatus expectedStatusCode) {
        return tryToGetResponseBody(() -> {
                    RestTemplate restTemplate = new RestTemplate();

                    ResponseEntity<List<T>> responseEntity = restTemplate.exchange(
                            getAllUrl,
                            HttpMethod.GET,
                            null,
                            typeReference
                    );

                    assertEquals(expectedStatusCode, responseEntity.getStatusCode());
                    List<T> entitiesList = responseEntity.getBody();
                    assertNotNull(entitiesList);
                    assertFalse(entitiesList.isEmpty());

                    return entitiesList;
                },
                expectedStatusCode);
    }

    /**
     * Update an entity via POST HTTP-method, check status codes of response, provide necessary assertions,
     * and return body of the response
     *
     * @param entity             the entity, which should be added
     * @param putUrl             an URL of receiving POST request REST-resource
     * @param expectedStatusCode expected HTTP status code of response
     * @param <T>                type of the entity <em>both of request and response</em>
     * @return PUT response body
     */
    public static <T> T getResponseFromEntityUpdateRequest(T entity, String putUrl, HttpStatus expectedStatusCode) {
        T putEntity = getBodyOfResponseToSendEntityRequest(entity, putUrl, HttpMethod.PUT, expectedStatusCode);
        assertEquals(entity, putEntity);
        return putEntity;
    }

    /**
     * Delete an entity via DELETE HTTP-method, check status codes of response, provide necessary assertions,
     * and return body of the response
     *
     * @param id                 identifier of the entity, which should be deleted
     * @param entityClass        class of the entity
     * @param deleteUrl          an URL of receiving DELETE request REST-resource
     * @param expectedStatusCode expected HTTP status code of response
     * @param <T>                type of the entity in response body
     * @return DELETE response body
     */
    public static <T> T getResponseFromEntityDeleteRequest(Long id, Class<T> entityClass, String deleteUrl,
                                                           HttpStatus expectedStatusCode) {
        return tryToGetResponseBody(() -> {
                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<T> responseEntity = restTemplate.exchange(
                            deleteUrl,
                            HttpMethod.DELETE,
                            null,
                            entityClass,
                            id);

                    assertEquals(expectedStatusCode, responseEntity.getStatusCode());

                    return responseEntity.getBody();
                },
                expectedStatusCode);
    }

    /**
     * Perform continuous addition of entities, supplied with a given supplier, to REST resource.
     * For example, the supplier could be a method link to {@link MockFactory#getMockCustomer()}):<br>.
     * <code>MockFactory::getMockCustomer</code><br>
     * Each entity undergoes POST request to REST resource with a given URL
     *
     * @param entitySupplier     supplier of entities
     * @param amount             amount of entities to create
     * @param postUrl            an URL of receiving POST request REST-resource
     * @param expectedStatusCode expected HTTP status code response to each POST request
     * @param <T>                type of entities <em>both of request and response</em>
     * @return list of the entities created on REST-resource
     */
    public static <T> List<T> getCreatedEntitiesList(Supplier<T> entitySupplier, int amount,
                                                     String postUrl, HttpStatus expectedStatusCode) {
        Function<T, T> toEntityFromPostRequest = (T entity) ->
                getResponseFromEntityAddRequest(entity, postUrl, expectedStatusCode);

        return Stream
                .generate(entitySupplier)
                .limit(amount)
                .map(toEntityFromPostRequest)
                .collect(Collectors.toList());
    }

    /**
     * Check sending of an entity with incorrect field (with incorrect value or with violating constraint) to a given
     * REST resource. Field is changed via java reflection mechanisms. <br>
     * Method checks for expected exception and, in a case of its' absence, it will fail with assertion error
     * <em>NOTICE: This behaviour will be changed after REST layer response codes regulation</em>
     *
     * @param entity                  an entity (eg. mock entity supplier)
     * @param fieldName               a field, which should be changed
     * @param incorrectValue          incorrect value for changing field
     * @param url                     URL of receiving POST request REST-resource
     * @param expectedErrorStatusCode expected HTTP status code error
     * @param <T>                     type of the entity <em>both of request and response</em>
     * @param <V>                     type of the filed
     */
    public static <T, V> void sendEntityWithIncorrectField(T entity, String fieldName, V incorrectValue, String url,
                                                           HttpMethod httpMethod, HttpStatus expectedErrorStatusCode)
            throws NoSuchFieldException, IllegalAccessException {
        setField(entity, fieldName, incorrectValue);
        getBodyOfResponseToSendEntityRequest(entity, url, httpMethod, expectedErrorStatusCode);
        fail("No expected exception occurs during sending of an entity with incorrect field");
    }

    /**
     * Get class of an entity and suppress warnings
     *
     * @param entity the entity, which class should be find out
     * @param <T>    type of an entity
     * @return class of the entity
     */
    @SuppressWarnings("unchecked")
    private static <T> Class<T> getEntityClass(T entity) {
        return (Class<T>) entity.getClass();
    }

    /**
     * Set field via java reflection
     *
     * @param entity     an entity, which value should be changed
     * @param fieldName  name of changing field
     * @param fieldValue value, which will be set to the given field
     * @param <T>        type of the entity
     * @param <V>        type of the field
     * @throws NoSuchFieldException   if field with specified fieldName not found
     * @throws IllegalAccessException in case of access errors
     */
    private static <T, V> void setField(T entity, String fieldName, V fieldValue)
            throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = entity.getClass().getDeclaredField(fieldName);
        declaredField.setAccessible(true);
        declaredField.set(entity, fieldValue);
        declaredField.setAccessible(false);
    }

    /**
     * Send an entity to a given REST resource via specified HTTP-method, check status codes of response, provide
     * necessary assertions, and return body of the response
     *
     * @param <T>                type of the entity <em>both of request and response</em>
     * @param entity             the entity, sent to REST resource
     * @param url                an URL of REST resource
     * @param httpMethod         method used to send data
     * @param expectedStatusCode expected HTTP status code of response
     * @return response body
     */
    private static <T> T getBodyOfResponseToSendEntityRequest(T entity, String url, HttpMethod httpMethod,
                                                              HttpStatus expectedStatusCode) {
        return tryToGetResponseBody(() -> {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
                    HttpEntity<T> httpEntity = new HttpEntity<>(entity, httpHeaders);

                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<T> responseEntity = restTemplate.exchange(
                            url,
                            httpMethod,
                            httpEntity,
                            getEntityClass(entity));

                    assertEquals(expectedStatusCode, responseEntity.getStatusCode());
                    T responseBody = responseEntity.getBody();
                    assertNotNull(responseBody);

                    return responseBody;
                },
                expectedStatusCode);
    }

    /**
     * Execute code wrapped in supplicant code, which should be designed to send a request to and to get back
     * a response from some REST resource, and to return response body. Wrapped code checked for
     * {@link HttpClientErrorException} and {@link HttpServerErrorException}.
     *
     * @param supplicant         supplicant code, producing some response body
     * @param expectedStatusCode expected status code in case of errors
     * @param <T>                type of response body, eg. entity type
     * @return response body
     */
    private static <T> T tryToGetResponseBody(RestSupplicant<T> supplicant, HttpStatus expectedStatusCode) {
        T responseBody = null;
        try {
            responseBody = supplicant.getResponseBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            assertEquals(expectedStatusCode, e.getStatusCode());
        }

        return responseBody;
    }
}
