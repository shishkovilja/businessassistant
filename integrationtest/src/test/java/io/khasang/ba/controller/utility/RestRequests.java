package io.khasang.ba.controller.utility;

import io.khasang.ba.entity.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public static final String CATEGORY_ROOT = REST_ROOT + "category";
    public static final String CUSTOMER_ROOT = REST_ROOT + "customer";
    public static final String CUSTOMER_REQUEST_STAGE_ROOT = REST_ROOT + "customer_request_stage";
    public static final String CUSTOMER_REQUEST_STAGE_NAME_ROOT = REST_ROOT + "customer_request_stage_name";
    public static final String OPERATOR_ROOT = REST_ROOT + "operator";
    public static final String POINT_OF_INTEREST_ROOT = REST_ROOT + "pointOfInterest";

    // Common addresses of REST resources
    public static final String ADD_PATH = "/add";
    public static final String GET_BY_ID_PATH = "/get/{id}";
    public static final String GET_ALL_PATH = "/get/all";
    public static final String UPDATE_PATH = "/update";
    public static final String DELETE_BY_ID_PATH = "/delete/{id}";

    /**
     * Map which determines root of rest resources by entity class, therefore there is no necessity to specify path
     * of the REST resource, because it will be detected automatically
     */
    public static final Map<Class<?>, String> restRootsMap = Collections.unmodifiableMap(new HashMap<Class<?>, String>() {{
        put(Category.class, CATEGORY_ROOT);
        put(Customer.class, CUSTOMER_ROOT);
        put(CustomerRequestStage.class, CUSTOMER_REQUEST_STAGE_ROOT);
        put(CustomerRequestStageName.class, CUSTOMER_REQUEST_STAGE_NAME_ROOT);
        put(Operator.class, OPERATOR_ROOT);
        put(PointOfInterest.class, POINT_OF_INTEREST_ROOT);
    }});

    /**
     * Map which determines maps class to parametrized type reference for lists of entities in order to avoid boilerplate code
     */
    public static final Map<Class<?>, ParameterizedTypeReference<? extends List>> typeReferencesMap =
            new HashMap<Class<?>, ParameterizedTypeReference<? extends List>>() {{
                put(Category.class, new ParameterizedTypeReference<List<Category>>() {
                });
                put(Customer.class, new ParameterizedTypeReference<List<Customer>>() {
                });
                put(CustomerRequestStage.class, new ParameterizedTypeReference<List<CustomerRequestStage>>() {
                });
                put(CustomerRequestStageName.class, new ParameterizedTypeReference<List<CustomerRequestStageName>>() {
                });
                put(Operator.class, new ParameterizedTypeReference<List<Operator>>() {
                });
                put(PointOfInterest.class, new ParameterizedTypeReference<List<PointOfInterest>>() {
                });
            }};

    /**
     * Add an entity via POST HTTP-method, check status codes of response, provide necessary assertions,
     * and return body of the response. Path to corresponding REST resource is detected by class by means of {@link #restRootsMap}
     *
     * @param entity             the entity, which should be added
     * @param expectedStatusCode expected HTTP status code of response
     * @param <T>                type of the entity <em>both of request and response</em>
     * @return POST response body
     */
    public static <T> T getResponseFromEntityAddRequest(T entity, HttpStatus expectedStatusCode) {
        return getBodyOfResponseToSendEntityRequest(entity, restRootsMap.get(entity.getClass()) + ADD_PATH,
                HttpMethod.POST, expectedStatusCode);
    }

    /**
     * Get an entity via GET HTTP-method, check status codes of response, provide necessary assertions,
     * and return body of the response. Path to corresponding REST resource is detected by class by means of {@link #restRootsMap}
     *
     * @param id                 of an entity which should be found
     * @param entityClass        class of the desired entity
     * @param expectedStatusCode expected HTTP status code of response
     * @param <T>                type of the entity returned in response body
     * @return GET response body, i.e. entity instance
     */
    public static <T> T getEntityById(Long id, Class<T> entityClass, HttpStatus expectedStatusCode) {
        return tryToGetResponseBody(() -> {
                    ResponseEntity<T> responseEntity = new RestTemplate().exchange(
                            restRootsMap.get(entityClass) + GET_BY_ID_PATH,
                            HttpMethod.GET,
                            null,
                            entityClass,
                            id);

                    assertEquals(expectedStatusCode, responseEntity.getStatusCode());
                    T responseBody = responseEntity.getBody();
                    assertNotNull(responseBody);

                    return responseBody;
                },
                expectedStatusCode);
    }

    /**
     * Get all entities via GET HTTP-method, check status codes of response, provide necessary assertions,
     * and return body of the response. Path to corresponding REST resource is detected by class by means of {@link #restRootsMap}<br>
     * Also, {@link #typeReferencesMap} used, to automate detection of necessary {@link ParameterizedTypeReference} by class.
     *
     * @param <T>                type of entities list returned in response body
     * @param expectedStatusCode expected HTTP status code of response
     * @return list of all entities from the given resource
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getAllEntitiesList(Class<T> entityClass,
                                                 HttpStatus expectedStatusCode) {
        return tryToGetResponseBody(() -> {
                    ResponseEntity<List<T>> responseEntity = new RestTemplate().exchange(
                            restRootsMap.get(entityClass) + GET_ALL_PATH,
                            HttpMethod.GET,
                            null,
                            (ParameterizedTypeReference<List<T>>) typeReferencesMap.get(entityClass)
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
     * and return body of the response. Path to corresponding REST resource is detected by class by means of {@link #restRootsMap}
     *
     * @param entity             the entity, which should be added
     * @param expectedStatusCode expected HTTP status code of response
     * @param <T>                type of the entity <em>both of request and response</em>
     * @return PUT response body
     */
    public static <T> T getResponseFromEntityUpdateRequest(T entity, HttpStatus expectedStatusCode) {
        T putEntity = getBodyOfResponseToSendEntityRequest(
                entity,
                restRootsMap.get(entity.getClass()) + UPDATE_PATH,
                HttpMethod.PUT,
                expectedStatusCode);

        assertEquals(entity, putEntity);
        return putEntity;
    }

    /**
     * Delete an entity via DELETE HTTP-method, check status codes of response, provide necessary assertions,
     * and return body of the response. Path to corresponding REST resource is detected by class by means of {@link #restRootsMap}
     *
     * @param id                 identifier of the entity, which should be deleted
     * @param entityClass        class of the entity
     * @param expectedStatusCode expected HTTP status code of response
     * @param <T>                type of the entity in response body
     * @return DELETE response body
     */
    public static <T> T getResponseFromEntityDeleteRequest(Long id, Class<T> entityClass,
                                                           HttpStatus expectedStatusCode) {
        return tryToGetResponseBody(() -> {
                    ResponseEntity<T> responseEntity = new RestTemplate().exchange(
                            restRootsMap.get(entityClass) + DELETE_BY_ID_PATH,
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
     * Perform continuous addition of group entities. Entities are created by a supplier, found by class, acting as key
     * for {@link MockFactory#mockSuppliersMap}. Path of corresponding REST resource is detected
     * in the same way by means of {@link #restRootsMap}
     * For example, the supplier could be a method link to {@link MockFactory#getMockCustomer()}):<br>.
     * <code>MockFactory::getMockCustomer</code><br>
     * Each entity undergoes POST request to REST resource with a given URL
     *
     * @param entityClass        an entity class
     * @param amount             amount of entities to create
     * @param expectedStatusCode expected HTTP status code response to each POST request
     * @param <T>                type of entities <em>both of request and response</em>
     * @return list of the entities created on REST-resource
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getCreatedEntitiesList(Class<T> entityClass, int amount, HttpStatus expectedStatusCode) {
        Function<T, T> toEntityFromPostRequest = (T entity) ->
                getResponseFromEntityAddRequest(entity, expectedStatusCode);
        Supplier<T> entitySupplier = (Supplier<T>) MockFactory.mockSuppliersMap.get(entityClass);

        return Stream
                .generate(entitySupplier)
                .limit(amount)
                .map(toEntityFromPostRequest)
                .collect(Collectors.toList());
    }

    /**
     * <p>Check addition of an entity with incorrect field (with incorrect value or with violating such constraints, as NotNull, Unique)
     * to corresponding REST resource, which path is detected by class by means of {@link #restRootsMap}.
     * Field value is set via java reflection mechanisms. </p>
     * <p>In a case of absence of {@link HttpClientErrorException} or {@link HttpServerErrorException}, method fails with assertion error
     * <em>NOTICE: This behaviour will be changed after REST layer response codes regulation</em></p>
     *
     * @param entityClass             an entity class
     * @param fieldName               a field, which should be changed
     * @param incorrectValue          incorrect value for changing field
     * @param expectedErrorStatusCode expected HTTP status code error
     * @param <T>                     type of the entity <em>both of request and response</em>
     * @param <V>                     type of the field
     */
    @SuppressWarnings("unchecked")
    public static <T, V> void addEntityWithIncorrectField(Class<T> entityClass, String fieldName, V incorrectValue,
                                                          HttpStatus expectedErrorStatusCode) {
        Supplier<T> entitySupplier = (Supplier<T>) MockFactory.mockSuppliersMap.get(entityClass);
        sendEntityWithIncorrectField(
                entitySupplier.get(),
                fieldName,
                incorrectValue,
                restRootsMap.get(entityClass) + ADD_PATH,
                HttpMethod.POST,
                expectedErrorStatusCode);
    }

    /**
     * <p>Check update of an entity with incorrect field (with incorrect value or with violating such constraints, as NotNull, Unique)
     * at corresponding REST resource, which path is detected by class by means of {@link #restRootsMap}.
     * Field value is set via java reflection mechanisms.</p>
     * <p>First of all, entity is properly created at REST resource, after that incorrect value is set to a given field and attempt
     * to update REST resource is performed.</p>
     * <p>In a case of absence of {@link HttpClientErrorException} or {@link HttpServerErrorException}, method fails with assertion error
     * <em>NOTICE: This behaviour will be changed after REST layer response codes regulation</em></p>
     *
     * @param entityClass             an entity class
     * @param fieldName               a field, which should be changed
     * @param incorrectValue          incorrect value for changing field
     * @param expectedErrorStatusCode expected HTTP status code error
     * @param <T>                     type of the entity <em>both of request and response</em>
     * @param <V>                     type of the field
     */
    @SuppressWarnings("unchecked")
    public static <T, V> void updateEntityWithIncorrectField(Class<T> entityClass, String fieldName, V incorrectValue,
                                                             HttpStatus expectedErrorStatusCode) {
        Supplier<T> entitySupplier = (Supplier<T>) MockFactory.mockSuppliersMap.get(entityClass);
        T entity = getResponseFromEntityAddRequest(entitySupplier.get(), HttpStatus.CREATED);

        sendEntityWithIncorrectField(
                entity,
                fieldName,
                incorrectValue,
                restRootsMap.get(entityClass) + UPDATE_PATH,
                HttpMethod.PUT,
                expectedErrorStatusCode);
    }

    /**
     * <p>Check update of an entity with incorrect field (with incorrect value or with violating such constraints, as NotNull, Unique)
     * at corresponding REST resource, which path is detected by class by means of {@link #restRootsMap}.
     * Field value is set via java reflection mechanisms.</p>
     * <p>Difference with {@link #updateEntityWithIncorrectField(Class, String, Object, HttpStatus)} consist in that this method
     * performs update for <em>properly created entity from REST resource</em>.
     * <p>In a case of absence of {@link HttpClientErrorException} or {@link HttpServerErrorException}, method fails with assertion error
     * <em>NOTICE: This behaviour will be changed after REST layer response codes regulation</em></p>
     *
     * @param entity                  an entity class
     * @param fieldName               a field, which should be changed
     * @param incorrectValue          incorrect value for changing field
     * @param expectedErrorStatusCode expected HTTP status code error
     * @param <T>                     type of the entity <em>both of request and response</em>
     * @param <V>                     type of the field
     */
    public static <T, V> void updateEntityWithIncorrectField(T entity, String fieldName, V incorrectValue,
                                                             HttpStatus expectedErrorStatusCode) {
        sendEntityWithIncorrectField(
                entity,
                fieldName,
                incorrectValue,
                restRootsMap.get(entity.getClass()) + UPDATE_PATH,
                HttpMethod.PUT,
                expectedErrorStatusCode);
    }

    /**
     * Check sending of an entity with incorrect field (with incorrect value or with violating such constraints, as NotNull, Unique)
     * to a given REST resource. Field is changed via java reflection mechanisms. <br>
     * Method checks for expected exception and, in a case of its' absence, it will fail with assertion error
     * <em>NOTICE: This behaviour will be changed after REST layer response codes regulation</em>
     *
     * @param entity                  an entity, which should be sent
     * @param fieldName               a field, which should be changed
     * @param incorrectValue          incorrect value for changing field
     * @param url                     URL of receiving POST request REST-resource
     * @param expectedErrorStatusCode expected HTTP status code error
     * @param <T>                     type of the entity <em>both of request and response</em>
     * @param <V>                     type of the field
     */
    private static <T, V> void sendEntityWithIncorrectField(T entity, String fieldName,
                                                            V incorrectValue, String url, HttpMethod httpMethod,
                                                            HttpStatus expectedErrorStatusCode) {
        try {
            setField(entity, fieldName, incorrectValue);
            getBodyOfResponseToSendEntityRequest(entity, url, httpMethod, expectedErrorStatusCode);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail(e.toString());
        }
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

                    ResponseEntity<T> responseEntity = new RestTemplate().exchange(
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
