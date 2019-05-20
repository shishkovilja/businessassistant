package io.khasang.ba.controller;

import io.khasang.ba.entity.CustomerRequestStage;
import io.khasang.ba.service.CustomerRequestStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for REST layer of CustomerRequestStage management: provided POST, GET, PUT and DELETE functionality
 */
@RestController
@RequestMapping(value = "/customer_request_stage")
// TODO ControllerAdvice and throwing of an exception in service layer
public class CustomerRequestStageController {

    @Autowired
    private CustomerRequestStageService customerRequestStageService;

    @PostMapping(value = "/add", consumes = "application/json;charset=utf-8")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerRequestStage addCustomerRequestStage(@RequestBody CustomerRequestStage newCustomerRequestStage) {
        return customerRequestStageService.addCustomerRequestStage(newCustomerRequestStage);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity getCustomerRequestStageById(@PathVariable(value = "id") long id) {
        CustomerRequestStage customerRequestStage = customerRequestStageService.getCustomerRequestStageById(id);

        return customerRequestStage != null ?
                ResponseEntity.ok(customerRequestStage) : ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/update", consumes = "application/json;charset=utf-8")
    // TODO Check updating of nonexistent entity
    public CustomerRequestStage updateCustomerRequestStage(@RequestBody CustomerRequestStage updatedCustomerRequestStage) {
        return customerRequestStageService.updateCustomerRequestStage(updatedCustomerRequestStage);
    }

    @GetMapping(value = "/get/all")
    public List<CustomerRequestStage> getAllCustomerRequestStages() {
        return customerRequestStageService.getAllCustomerRequestStages();
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomerRequestStage(@PathVariable(value = "id") long id) {
        customerRequestStageService.deleteCustomerRequestStage(id);
    }
}
