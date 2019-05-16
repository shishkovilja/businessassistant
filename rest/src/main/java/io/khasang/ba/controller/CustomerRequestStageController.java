package io.khasang.ba.controller;

import io.khasang.ba.entity.CustomerRequestStage;
import io.khasang.ba.service.CustomerRequestStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for REST layer of CustomerRequestStage management: provided POST, GET, PUT and DELETE functionality
 */
@Controller
@RequestMapping(value = "/customer_request_stage")
public class CustomerRequestStageController {

    @Autowired
    private CustomerRequestStageService customerRequestStageService;

    @PostMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    public CustomerRequestStage addCustomerRequestStage(@RequestBody CustomerRequestStage newCustomerRequestStage) {
        return customerRequestStageService.addCustomerRequestStage(newCustomerRequestStage);
    }

    @GetMapping(value = "/get/{id}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public CustomerRequestStage getCustomerRequestStageById(@PathVariable(value = "id") long id) {
        return customerRequestStageService.getCustomerRequestStageById(id);
    }

    @PutMapping(value = "/update", produces = "application/json;charset=utf-8")
    @ResponseBody
    public CustomerRequestStage updateCustomerRequestStage(@RequestBody CustomerRequestStage updatedCustomerRequestStage) {
        return customerRequestStageService.updateCustomerRequestStage(updatedCustomerRequestStage);
    }

    @GetMapping(value = "/get/all", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<CustomerRequestStage> getAllCustomerRequestStages() {
        return customerRequestStageService.getAllCustomerRequestStages();
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public CustomerRequestStage deleteCustomerRequestStage(@PathVariable(value = "id") long id) {
        return customerRequestStageService.deleteCustomerRequestStage(id);
    }
}
