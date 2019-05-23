package io.khasang.ba.controller;

import io.khasang.ba.entity.Customer;
import io.khasang.ba.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for REST layer of Customer management: provided POST, GET, PUT and DELETE functionality
 */
@RestController
@RequestMapping(value = "/customer")
// TODO ControllerAdvice and throwing of an exception in service layer
public class CustomerController {

    @Autowired
    private CustomerService CustomerService;

    @PostMapping(value = "/add", consumes = "application/json;charset=utf-8")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer addCustomer(@RequestBody Customer newCustomer) {
        return CustomerService.addCustomer(newCustomer);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") long id) {
        Customer customer = CustomerService.getCustomerById(id);

        return (customer != null) ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/update", consumes = "application/json;charset=utf-8")
    public Customer updateCustomer(@RequestBody Customer updatedCustomer) {
        return CustomerService.updateCustomer(updatedCustomer);
    }

    @GetMapping(value = "/get/all")
    public List<Customer> getAllCustomers() {
        return CustomerService.getAllCustomers();
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable(value = "id") long id) {
        CustomerService.deleteCustomer(id);
    }
}
