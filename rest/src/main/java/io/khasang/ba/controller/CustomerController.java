package io.khasang.ba.controller;

import io.khasang.ba.entity.Customer;
import io.khasang.ba.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for REST layer of Customer management: provided POST, GET, PUT and DELETE functionality
 */
@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService CustomerService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Customer addCustomer(@RequestBody Customer newCustomer) {
        return CustomerService.addCustomer(newCustomer);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Customer getCustomerById(@PathVariable(value = "id") long id) {
        return CustomerService.getCustomerById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Customer updateCustomer(@RequestBody Customer updatedCustomer) {
        return CustomerService.updateCustomer(updatedCustomer);
    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Customer> getAllCustomers() {
        return CustomerService.getAllCustomers();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Customer deleteCustomer(@PathVariable(value = "id") long id) {
        return CustomerService.deleteCustomer(id);
    }
}
