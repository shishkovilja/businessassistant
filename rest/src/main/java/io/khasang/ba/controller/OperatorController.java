package io.khasang.ba.controller;

import io.khasang.ba.entity.Operator;
import io.khasang.ba.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for REST layer of Operator management: provided POST, GET, PUT and DELETE functionality
 */
@Controller
@RequestMapping(value = "/operator")
public class OperatorController {

    @Autowired
    private OperatorService operatorService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Operator addOperator(@RequestBody Operator newOperator) {
        return operatorService.addOperator(newOperator);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Operator getOperatorById(@PathVariable(value = "id") long id) {
        return operatorService.getOperatorById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Operator updateOperator(@RequestBody Operator updatedOperator) {
        return operatorService.updateOperator(updatedOperator);
    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Operator> getAllOperators() {
        return operatorService.getAllOperators();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Operator deleteOperator(@PathVariable(value = "id") long id) {
        return operatorService.deleteOperator(id);
    }
}
