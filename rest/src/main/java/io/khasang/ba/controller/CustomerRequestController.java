package io.khasang.ba.controller;

import io.khasang.ba.entity.CustomerRequest;
import io.khasang.ba.service.CustomerRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customer_request", produces = "application/json;charset=utf-8")
public class CustomerRequestController {

    @Autowired
    private CustomerRequestService customerRequestService;

    @PostMapping(value = "/add", consumes = "application/json;charset=utf-8")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerRequest addCustomerRequest(@RequestBody CustomerRequest customerRequest) {
        return customerRequestService.addCustomerRequest(customerRequest);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<CustomerRequest> getCustomerRequestById(@PathVariable(value = "id") long id) {
        CustomerRequest customerRequest = customerRequestService.getCustomerRequestById(id);

        return (customerRequest != null) ? ResponseEntity.ok(customerRequest) : ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/get/all")
    public List<CustomerRequest> getAllCustomerRequests() {
        return customerRequestService.getAllCustomerRequests();
    }

    @PutMapping(value = "/update", consumes = "application/json;charset=utf-8")
    public CustomerRequest updateCustomerRequest(@RequestBody CustomerRequest customerRequest) {
        return customerRequestService.updateCustomerRequest(customerRequest);
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomerRequest(@PathVariable(value = "id") long id) {
        customerRequestService.deleteCustomerRequest(id);
    }
}
