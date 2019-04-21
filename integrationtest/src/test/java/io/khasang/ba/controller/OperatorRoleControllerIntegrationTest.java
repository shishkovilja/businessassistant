package io.khasang.ba.controller;

import io.khasang.ba.entity.OperatorRole;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Integration test for Role REST layer
 */
public class OperatorRoleControllerIntegrationTest {
    private static final String TEST_ROLE_NAME_PREFIX = "TEST_ROLE_";
    private static final String TEST_ROLE_DESCRIPTION = "Test role";
    private static final int TEST_ENTITIES_COUNT = 30;

    private static final String ROOT = "http://localhost:8080/role";
    private static final String ADD = "/add";
    private static final String GET_BY_ID = "/get/{id}";
    private static final String GET_ALL = "/get/all";
    private static final String UPDATE = "/update";
    private static final String DELETE_BY_ID = "/delete/{id}";

    /**
     * Check role addition
     */
    @Test
    public void checkAddRole() {
        OperatorRole createdOperatorRole = getCreatedRole();
        OperatorRole receivedOperatorRole = getRoleById(createdOperatorRole.getId());
        assertNotNull(receivedOperatorRole);
        assertEquals(createdOperatorRole, receivedOperatorRole);
    }

    /**
     * Check role name uniqueness constraint. It must be impossible to add role
     * with duplicate role name value
     */
    @Test(expected = HttpServerErrorException.class)
    public void checkRoleNameUniqueness() {
        OperatorRole createdOperatorRole = getCreatedRole();
        OperatorRole duplicateOperatorRole = new OperatorRole();
        duplicateOperatorRole.setName(createdOperatorRole.getName());
        getRoleResponseEntityFromAdditionRequest(duplicateOperatorRole);
    }

    /**
     * Checks sequential addition of certain amount of roles addition and getting. Amount is set in
     * {@link #TEST_ENTITIES_COUNT} constant
     */
    @Test
    public void checkGetAllRoles() {
        List<OperatorRole> createdOperatorRoles = new ArrayList<>(TEST_ENTITIES_COUNT);
        for (int i = 0; i < TEST_ENTITIES_COUNT; i++) {
            createdOperatorRoles.add(getCreatedRole());
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<OperatorRole>> responseEntity = restTemplate.exchange(
                ROOT + GET_ALL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OperatorRole>>() {
                }
        );
        List<OperatorRole> allReceivedOperatorRoles = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(allReceivedOperatorRoles);
        assertFalse(allReceivedOperatorRoles.isEmpty());

        List<OperatorRole> receivedRolesSubList =
                allReceivedOperatorRoles.subList(allReceivedOperatorRoles.size() - TEST_ENTITIES_COUNT, allReceivedOperatorRoles.size());
        for (int i = 0; i < TEST_ENTITIES_COUNT; i++) {
            assertEquals(createdOperatorRoles.get(i), receivedRolesSubList.get(i));
        }
    }

    /**
     * Check of role entity update via PUT request
     */
    @Test
    public void checkUpdateRole() {
        OperatorRole operatorRole = getCreatedRole();
        fillRole(operatorRole);
        putRoleToUpdate(operatorRole);
        OperatorRole updatedOperatorRole = getRoleById(operatorRole.getId());
        assertNotNull(updatedOperatorRole);
        assertEquals(operatorRole, updatedOperatorRole);
    }

    /**
     * Check of role deletion
     */
    @Test
    public void checkRoleDelete() {
        OperatorRole operatorRole = getCreatedRole();
        OperatorRole deletedOperatorRole = getDeletedRole(operatorRole.getId());
        assertEquals(operatorRole, deletedOperatorRole);
        assertNull(getRoleById(operatorRole.getId()));
    }

    /**
     * Utility method which deletes role by id and retrieves role entity from DELETE response body
     *
     * @param id Id of the role which should be deleted
     * @return Deleted role
     */
    private OperatorRole getDeletedRole(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OperatorRole> responseEntity = restTemplate.exchange(
                ROOT + DELETE_BY_ID,
                HttpMethod.DELETE,
                null,
                OperatorRole.class,
                id
        );
        OperatorRole deletedOperatorRole = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(deletedOperatorRole);
        return deletedOperatorRole;
    }

    /**
     * Method for role getting by id
     *
     * @param id Id in table of roles
     * @return Found {@link OperatorRole} instance
     */
    private OperatorRole getRoleById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OperatorRole> responseEntity = restTemplate.exchange(
                ROOT + GET_BY_ID,
                HttpMethod.GET,
                null,
                OperatorRole.class,
                id
        );
        OperatorRole receivedOperatorRole = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        return receivedOperatorRole;
    }

    /**
     * Put role for update
     *
     * @param operatorRole Role, which should be updated on service
     */
    private void putRoleToUpdate(OperatorRole operatorRole) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<OperatorRole> httpEntity = new HttpEntity<>(operatorRole, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OperatorRole> responseEntity = restTemplate.exchange(
                ROOT + UPDATE,
                HttpMethod.PUT,
                httpEntity,
                OperatorRole.class
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    /**
     * Get created test role entity from POST response during role creation procedure. Instead of creating {@link OperatorRole}
     * instance by constructor, this method returns instance from response, thus created role contains table identifier
     *
     * @return Instance of {@link OperatorRole} with generated identifier
     */
    private OperatorRole getCreatedRole() {
        OperatorRole operatorRole = new OperatorRole();
        fillRole(operatorRole);
        ResponseEntity<OperatorRole> responseEntity = getRoleResponseEntityFromAdditionRequest(operatorRole);
        OperatorRole createdOperatorRole = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(createdOperatorRole);
        assertNotNull(createdOperatorRole.getId());
        return createdOperatorRole;
    }

    /**
     * Add role entity via POST request
     *
     * @param operatorRole {@link OperatorRole} instance, which should be added via POST request
     * @return {@link ResponseEntity} containing response data
     */
    private ResponseEntity<OperatorRole> getRoleResponseEntityFromAdditionRequest(OperatorRole operatorRole) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<OperatorRole> httpEntity = new HttpEntity<>(operatorRole, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                ROOT + ADD,
                HttpMethod.POST,
                httpEntity,
                OperatorRole.class
        );
    }

    /**
     * Fill test role entity with unique role name for further persistence process.
     * {@link UUID} suffix is used to provide name uniqueness
     */
    private void fillRole(OperatorRole operatorRole) {
        operatorRole.setName(TEST_ROLE_NAME_PREFIX + UUID.randomUUID().toString());
        operatorRole.setDescription(TEST_ROLE_DESCRIPTION);
    }
}
