package io.khasang.ba.controller;

import io.khasang.ba.entity.CustomerRequestType;
import io.khasang.ba.service.CustomerRequestTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for REST layer of customer request type management: provided POST, GET, PUT and DELETE functionality
 */
@Controller
@RequestMapping(value = "/customer_request_type")
public class CustomerRequestTypeController {

    @Autowired
    private CustomerRequestTypeService customerRequestTypeService;

    @PostMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    public CustomerRequestType addRole(@RequestBody CustomerRequestType newCustomerRequestType) {
        return customerRequestTypeService.addCustomerRequestType(newCustomerRequestType);
    }

    @GetMapping(value = "/get/{id}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public CustomerRequestType getRoleById(@PathVariable(value = "id") long id) {
        return customerRequestTypeService.getCustomerRequestTypeById(id);
    }

    @PutMapping(value = "/update", produces = "application/json;charset=utf-8")
    @ResponseBody
    public CustomerRequestType updateRole(@RequestBody CustomerRequestType updatedCustomerRequestType) {
        return customerRequestTypeService.updateCustomerRequestType(updatedCustomerRequestType);
    }

    @GetMapping(value = "/get/all", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<CustomerRequestType> getAllRoles() {
        return customerRequestTypeService.getAllCustomerRequestTypes();
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public CustomerRequestType deleteRole(@PathVariable(value = "id") long id) {
        return customerRequestTypeService.deleteCustomerRequestType(id);
    }
}
