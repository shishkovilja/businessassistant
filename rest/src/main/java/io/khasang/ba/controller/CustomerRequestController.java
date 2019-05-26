package io.khasang.ba.controller;

import io.khasang.ba.entity.CustomerRequest;
import io.khasang.ba.service.CustomerRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer_request")
public class CustomerRequestController {

    @Autowired
    private CustomerRequestService customerRequestService;

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public CustomerRequest addCustomerRequest(@RequestBody CustomerRequest customerRequest) {
        customerRequestService.addCustomerRequest(customerRequest);
        return customerRequest;
    }

    @ResponseBody
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public CustomerRequest getCustomerRequestById(@PathVariable(value = "id") long id) {
        return customerRequestService.getCustomerRequestById(id);
    }

    @ResponseBody
    @RequestMapping(value = "/get/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public List<CustomerRequest> getAllCustomerRequests() {
        return customerRequestService.getAllCustomerRequests();
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    public CustomerRequest updateCustomerRequest(@RequestBody CustomerRequest customerRequest) {
        return customerRequestService.updateCustomerRequest(customerRequest);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
    public CustomerRequest deleteCustomerRequest(@PathVariable(value = "id") long id) {
        return customerRequestService.deleteCustomerRequest(id);
    }
}
