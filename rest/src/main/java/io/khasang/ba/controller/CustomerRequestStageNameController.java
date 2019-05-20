package io.khasang.ba.controller;

import io.khasang.ba.entity.CustomerRequestStageName;
import io.khasang.ba.service.CustomerRequestStageNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for REST layer of CustomerRequestStageName management: provided POST, GET, PUT and DELETE functionality
 */
@RestController
@RequestMapping(value = "/customer_request_stage_name")
// TODO ControllerAdvice and throwing of an exception in service layer
public class CustomerRequestStageNameController {

    @Autowired
    private CustomerRequestStageNameService CustomerRequestStageNameService;

    @PostMapping(value = "/add", consumes = "application/json;charset=utf-8")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerRequestStageName addCustomerRequestStageName(@RequestBody CustomerRequestStageName newCustomerRequestStageName) {
        return CustomerRequestStageNameService.addCustomerRequestStageName(newCustomerRequestStageName);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity getCustomerRequestStageNameById(@PathVariable(value = "id") long id) {
        CustomerRequestStageName CustomerRequestStageName = CustomerRequestStageNameService.getCustomerRequestStageNameById(id);

        return CustomerRequestStageName != null ?
                ResponseEntity.ok(CustomerRequestStageName) : ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/update", consumes = "application/json;charset=utf-8")
    // TODO Check updating of nonexistent entity
    public CustomerRequestStageName updateCustomerRequestStageName(@RequestBody CustomerRequestStageName updatedCustomerRequestStageName) {
        return CustomerRequestStageNameService.updateCustomerRequestStageName(updatedCustomerRequestStageName);
    }

    @GetMapping(value = "/get/all")
    public List<CustomerRequestStageName> getAllCustomerRequestStageNames() {
        return CustomerRequestStageNameService.getAllCustomerRequestStageNames();
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    // TODO Check DELETE non-existent entity
    public void deleteCustomerRequestStageName(@PathVariable(value = "id") long id) {
        CustomerRequestStageNameService.deleteCustomerRequestStageName(id);
    }
}
