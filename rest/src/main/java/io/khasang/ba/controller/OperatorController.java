package io.khasang.ba.controller;

import io.khasang.ba.entity.Operator;
import io.khasang.ba.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for REST layer of Operator management: provided POST, GET, PUT and DELETE functionality
 */
@RestController
@RequestMapping(value = "/operator")
public class OperatorController {

    @Autowired
    private OperatorService operatorService;

    @PostMapping(value = "/add", consumes = "application/json;charset=utf-8")
    @ResponseStatus(HttpStatus.CREATED)
    public Operator addOperator(@RequestBody Operator newOperator) {
        return operatorService.addOperator(newOperator);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Operator> getOperatorById(@PathVariable(value = "id") long id) {
        Operator operator = operatorService.getOperatorById(id);

        return (operator != null) ? ResponseEntity.ok(operator) : ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/update", consumes = "application/json;charset=utf-8")
    public Operator updateOperator(@RequestBody Operator updatedOperator) {
        return operatorService.updateOperator(updatedOperator);
    }

    @GetMapping(value = "/get/all")
    public List<Operator> getAllOperators() {
        return operatorService.getAllOperators();
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOperator(@PathVariable(value = "id") long id) {
        operatorService.deleteOperator(id);
    }
}
