package io.khasang.ba.controller;

import io.khasang.ba.entity.CustomerRequest;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerRequestControllerIntegrationTest {

    private final static String ROOT = "http://localhost:8080/customer_request";
    private final static String ADD = "/add";
    private final static String GET_BY_ID = "/get";
    private static final String ALL = "/all";
    private static final String DELETE = "/delete";
    private static final String UPDATE = "/update";

    @Test
    public void addRequest() {
        CustomerRequest customerRequest = createdRequest();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CustomerRequest> responseEntity = restTemplate.exchange(
                ROOT + GET_BY_ID + "/{id}",
                HttpMethod.GET,
                null,
                CustomerRequest.class,
                customerRequest.getId()
        );

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void checkGetAllRequests() {
        createdRequest();
        createdRequest();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<CustomerRequest>> responseEntity = getListResponseEntity(restTemplate);
        List<CustomerRequest> customerRequests = responseEntity.getBody();

        for (CustomerRequest customerRequest : customerRequests) {
            assertNotNull(customerRequest);
        }
    }

    @Test
    public void deleteRequest() {
        createdRequest();
        CustomerRequest customerRequest = createdRequest();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<CustomerRequest>> responseEntity1 = getListResponseEntity(restTemplate);
        List<CustomerRequest> requests1 = responseEntity1.getBody();
        ResponseEntity<CustomerRequest> responseEntity = restTemplate.exchange(
                ROOT + DELETE + "/{id}",
                HttpMethod.DELETE,
                null,
                CustomerRequest.class,
                customerRequest.getId()
        );
        ResponseEntity<List<CustomerRequest>> responseEntity2 = getListResponseEntity(restTemplate);
        List<CustomerRequest> requests2 = responseEntity2.getBody();

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        assertEquals(1, requests1.size() - requests2.size());
    }

    @Test
    public void updateRequest() {
        CustomerRequest customerRequest = createdRequest();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        CustomerRequest customerRequestUpdate = prefillRequest(customerRequest.getId());
        HttpEntity<CustomerRequest> entity = new HttpEntity<>(customerRequestUpdate, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CustomerRequest> responseEntity = restTemplate.exchange(
                ROOT + UPDATE,
                HttpMethod.PUT,
                entity,
                CustomerRequest.class
        );
        CustomerRequest updatedCustomerRequest = responseEntity.getBody();

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        assertNotNull(updatedCustomerRequest);
        assertEquals(customerRequest.getId(), updatedCustomerRequest.getId());
    }

    private ResponseEntity<List<CustomerRequest>> getListResponseEntity(RestTemplate restTemplate) {
        return restTemplate.exchange(
                ROOT + GET_BY_ID + ALL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CustomerRequest>>() {
                }
        );
    }

    private CustomerRequest createdRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        CustomerRequest customerRequest = prefillRequest();
        HttpEntity<CustomerRequest> entity = new HttpEntity<>(customerRequest, headers);
        RestTemplate restTemplate = new RestTemplate();
        CustomerRequest createdCustomerRequest = restTemplate.exchange(
                ROOT + ADD,
                HttpMethod.POST,
                entity,
                CustomerRequest.class
        ).getBody();

        assertNotNull(createdCustomerRequest);
//        assertNotNull(createdCustomerRequest.getCategory());
        return createdCustomerRequest;
    }

    private CustomerRequest prefillRequest() {
        CustomerRequest customerRequest = new CustomerRequest();
//        customerRequest.setCategory("Трудовой договор");
//        customerRequest.setDescription("Ляляляля");
        return customerRequest;
    }

    private CustomerRequest prefillRequest(long id) {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setId(id);
//        customerRequest.setCategory("Трудовой договор");
//        customerRequest.setDescription("Траляляляля");
        return customerRequest;
    }
}
