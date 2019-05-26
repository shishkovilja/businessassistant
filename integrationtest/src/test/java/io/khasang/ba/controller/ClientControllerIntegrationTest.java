package io.khasang.ba.controller;

import io.khasang.ba.entity.Client;
import io.khasang.ba.entity.CustomerRequest;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ClientControllerIntegrationTest {

    private final static String ROOT = "http://localhost:8080/client";
    private final static String ADD = "/add";
    private final static String GET_BY_ID = "/get";
    private static final String ALL = "/all";
    private static final String DELETE = "/delete";
    private static final String UPDATE = "/update";

    @Test
    public void addClient() {
        Client client = createdClient();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Client> responseEntity = restTemplate.exchange(
                ROOT + GET_BY_ID + "/{id}",
                HttpMethod.GET,
                null,
                Client.class,
                client.getId()
        );

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void checkGetAllClient() {
        createdClient();
        createdClient();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Client>> responseEntity = getListResponseEntity(restTemplate);
        List<Client> allClient = responseEntity.getBody();

        for (Client client : allClient) {
            assertNotNull(client);
        }
    }

    @Test
    public void deleteClient() {
        createdClient();
        Client client = createdClient();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Client>> responseEntity1 = getListResponseEntity(restTemplate);
        List<Client> allClient1 = responseEntity1.getBody();
        ResponseEntity<Client> responseEntity = restTemplate.exchange(
                ROOT + DELETE + "/{id}",
                HttpMethod.DELETE,
                null,
                Client.class,
                client.getId()
        );
        ResponseEntity<List<Client>> responseEntity2 = getListResponseEntity(restTemplate);
        List<Client> allClient2 = responseEntity2.getBody();

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        assertEquals(1, allClient1.size() - allClient2.size());
    }

    @Test
    public void updateClient() {
        Client client = createdClient();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        Client clientUpdate = prefillClient(client.getId());
        HttpEntity<Client> entity = new HttpEntity<>(clientUpdate, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Client> responseEntity = restTemplate.exchange(
                ROOT + UPDATE,
                HttpMethod.PUT,
                entity,
                Client.class
        );
        Client updatedClient = responseEntity.getBody();

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        assertNotNull(updatedClient);
        assertEquals(client.getId(), updatedClient.getId());
    }

    private ResponseEntity<List<Client>> getListResponseEntity(RestTemplate restTemplate) {
        return restTemplate.exchange(
                ROOT + GET_BY_ID + ALL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Client>>() {
                }
        );
    }

    private Client createdClient() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        Client client = prefillClient();
        HttpEntity<Client> entity = new HttpEntity<>(client, headers);
        RestTemplate restTemplate = new RestTemplate();
        Client createdClient = restTemplate.exchange(
                ROOT + ADD,
                HttpMethod.POST,
                entity,
                Client.class
        ).getBody();

        assertNotNull(createdClient);
        assertNotNull(createdClient.getName());
        return createdClient;
    }

    private Client prefillClient() {
        Client client = new Client();
        client.setName("Anna");
        client.setSurname("Ivanova");
        client.setAddress("ul.Lesnaya,d.13,kv.15");
        client.setEmail("pochta1@gmail.com");
        client.setLogin("tralala");
        client.setTime(LocalTime.of(10, 11, 34));
        client.setDate(LocalDate.of(2018, 10, 12));

        CustomerRequest customerRequest1 = new CustomerRequest();
        CustomerRequest customerRequest2 = new CustomerRequest();

//        customerRequest1.setCategory("Трудовой договор");
//        customerRequest1.setDescription("Ляляляля");
//        customerRequest2.setCategory("Трудовой договор");
//        customerRequest2.setDescription("Tралялятра");

        List<CustomerRequest> customerRequestList = new ArrayList<>();
        customerRequestList.add(customerRequest1);
        customerRequestList.add(customerRequest1);

        client.setCustomerRequestList(customerRequestList);
        return client;
    }

    private Client prefillClient(long id) {
        Client client = new Client();
        client.setId(id);
        client.setName("Inga");
        client.setSurname("Petrova");
        client.setAddress("ul.Lesnaya,d.3,kv.5");
        client.setEmail("pochta@gmail.com");
        client.setLogin("lalala");
        client.setTime(LocalTime.of(10, 11, 34));
        client.setDate(LocalDate.of(2018, 10, 12));

        CustomerRequest customerRequest1 = new CustomerRequest();
        CustomerRequest customerRequest2 = new CustomerRequest();

//        customerRequest1.setCategory("Трудовой договор");
//        customerRequest1.setDescription("Ляляляляляля");
//        customerRequest2.setCategory("Трудовой договор");
//        customerRequest2.setDescription("Tралялятраляля");

        List<CustomerRequest> customerRequestList = new ArrayList<>();
        customerRequestList.add(customerRequest1);
        customerRequestList.add(customerRequest1);

        client.setCustomerRequestList(customerRequestList);
        return client;
    }
}
