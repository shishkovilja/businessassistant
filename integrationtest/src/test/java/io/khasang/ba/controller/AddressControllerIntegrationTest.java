package io.khasang.ba.controller;

import io.khasang.ba.entity.Address;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class AddressControllerIntegrationTest {
    private final String LAN_ADDRESS = "localhost";
    private final String PORT = "8080";
    private final String ROOT = "http://" + LAN_ADDRESS + ":" + PORT + "/address";
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
    public void deleteAddress() {
        Address address;
        ResponseEntity<Address> responseEntity;
        address = prefillAddress();
        address = createAddress(address);
        responseEntity = deleteAddressById(address.getId());

        // Check response is OK
        Assert.assertEquals("Request is bad. Must be OK", "OK", responseEntity.getStatusCode().getReasonPhrase());

        // Record must doesn't find into DB
        Assert.assertNull("Entity doesn't delete into DB", getAddressById(address.getId()));
    }

    /**
     * Test record entity
     * Requset must be record. Data don't count
     */
    @Test
    public void addAddress() {
        Address address;
        Address responseBodyAddress;
        address = prefillAddress();

        responseBodyAddress = createAddress(address);

        // Response create not null
        Assert.assertNotNull("Response must be Entity", responseBodyAddress);
        deleteAddressById(responseBodyAddress.getId());
    }

    /**
     * Read entity by Id
     * @param id - id Entity
     * @return entity
     */
    private Address getAddressById(long id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Address> responseEntity = restTemplate.exchange(
                ROOT + GET + ID,
                HttpMethod.GET,
                null,
                Address.class,
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
    public void addAddressWithEqualContent() {
        Address address;
        Address responseBodyAddress;
        address = prefillAddress();

        responseBodyAddress = createAddress(address);

        // Equals two entity created and template
        Assert.assertTrue("Fields don't equals. Must be equals", equals(address, responseBodyAddress));
        deleteAddressById(responseBodyAddress.getId());
    }

    /**
     * Entity must update something self fields
     */
    @Test
    public void updateAddress() {
        Address address;
        Address responseCreatedAddress;
        Address responseUpdateAddress;

        address = prefillAddress();
        responseCreatedAddress = createAddress(address);

        // Change data
        address.setCity("city2");
        address.setPostcode(111111);
        address.setId(responseCreatedAddress.getId());
        responseUpdateAddress = updateAddress(address);

        // Created address mustn't be equals updated address
        Assert.assertFalse("Fields are equals. They must be don't equals", equals(responseCreatedAddress, responseUpdateAddress));

        // Delete entity into DB
        deleteAddressById(responseUpdateAddress.getId());
    }

    @Test
    public void getAllAddresses() {
        List<Address> addresses;

        addresses = new ArrayList<>();
        addresses.add(createAddress(prefillAddress()));
        addresses.add(createAddress(prefillAddress()));
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Address>> responseEntity = restTemplate.exchange(
                ROOT + GET + ALL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Address>>() {
                }
        );
        Assert.assertEquals("Request is bad. Must be OK", "OK", responseEntity.getStatusCode().getReasonPhrase());
        Assert.assertNotNull("Not all addresses were recieve. Must be more", addresses.get(0));
        Assert.assertNotNull("Not all addresses were recieve. Must be more", addresses.get(1));
        deleteAddressById(addresses.get(0).getId());
        deleteAddressById(addresses.get(1).getId());
    }

     /**
     * Create record into DB
     * @param entity create entity
     * @return responseBody
     */
    private Address createAddress(Address entity) {
        RestTemplate restTemplate;
        HttpHeaders httpHeaders;
        HttpEntity<Address> httpEntity;
        Address createdAddress;

        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpEntity = new HttpEntity<>(entity, httpHeaders);

        createdAddress = restTemplate.exchange(
                ROOT + ADD,
                HttpMethod.POST,
                httpEntity,
                Address.class
        ).getBody();

        return createdAddress;
    }

    /**
     * Update record into DB
     * @param entity update entity
     * @return response body
     */
    private Address updateAddress(Address entity) {
        RestTemplate restTemplate;
        HttpHeaders httpHeaders;
        HttpEntity<Address> httpEntity;
        Address updateAddress;

        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpEntity = new HttpEntity<>(entity, httpHeaders);

        updateAddress = restTemplate.exchange(
                ROOT + UPDATE,
                HttpMethod.PUT,
                httpEntity,
                Address.class
        ).getBody();

        return  updateAddress;
    }

    /**
     * Delete entity into DB by Id
     * @param id - id entity
     * @return response action
     */
    private ResponseEntity<Address> deleteAddressById(long id) {
        RestTemplate restTemplate;
        restTemplate = new RestTemplate();

        return restTemplate.exchange(
                ROOT + DELETE + ID,
                HttpMethod.DELETE,
                null,
                Address.class,
                id
        );
    }

    /**
     * Create and fill Address
     * @return created Address
     */
    private Address prefillAddress() {

        return new Address(
                "region",
                "city",
                "street",
                105033,
                "33/2",
                "20",
                0.456000,
                3.123456
        );
    }

    /**
     * Compare Address entity
     * @param source - source entity
     * @param target - equals entity
     * @return is equals?
     */
    private boolean equals(Address source, Address target) {
        boolean isCheck = false;

        if (
                source.getCity().equals(target.getCity()) &&
                source.getHause().equals(target.getHause()) &&
                source.getLatitude() == target.getLatitude() &&
                source.getLongitude() == target.getLongitude() &&
                source.getOffice().equals(target.getOffice()) &&
                source.getPostcode() == target.getPostcode() &&
                source.getRegion().equals(target.getRegion()) &&
                source.getStreet().equals(target.getStreet())
        ) {
            isCheck = true;
        }


        return isCheck;
    }
}
