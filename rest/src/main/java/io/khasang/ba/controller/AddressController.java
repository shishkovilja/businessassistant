package io.khasang.ba.controller;

import io.khasang.ba.entity.Address;
import io.khasang.ba.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/get/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Address> getAll() {
        return addressService.getAllAddresses();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Address getAddress(@PathVariable(value = "id") long id) {
        return addressService.getAddressById(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Address addAddress(@RequestBody Address address) {
        return addressService.addAddress(address);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Address updateAddress(@RequestBody Address address) {
        return addressService.updateAddress(address);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Address deleteAddress(@PathVariable(value = "id") long id) {
        return  addressService.deleteAddress(id);
    }
}
