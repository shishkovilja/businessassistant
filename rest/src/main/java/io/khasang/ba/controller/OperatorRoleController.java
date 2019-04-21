package io.khasang.ba.controller;

import io.khasang.ba.entity.OperatorRole;
import io.khasang.ba.service.OperatorRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for REST layer of operators's role management: provided POST, GET, PUT and DELETE functionality
 */
@Controller
@RequestMapping(value = "/operator_role")
public class OperatorRoleController {

    @Autowired
    private OperatorRoleService operatorRoleService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public OperatorRole addRole(@RequestBody OperatorRole newOperatorRole) {
        operatorRoleService.addOperatorRole(newOperatorRole);
        return newOperatorRole;
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public OperatorRole getRoleById(@PathVariable(value = "id") long id) {
        return operatorRoleService.getOperatorRoleById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    @ResponseBody
    public OperatorRole updateRole(@RequestBody OperatorRole updatedOperatorRole) {
        return operatorRoleService.updateOperatorRole(updatedOperatorRole);
    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<OperatorRole> getAllRoles() {
        return operatorRoleService.getAllOperatorRoles();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
    @ResponseBody
    public OperatorRole deleteRole(@PathVariable(value = "id") long id) {
        return operatorRoleService.deleteOperatorRole(id);
    }
}
