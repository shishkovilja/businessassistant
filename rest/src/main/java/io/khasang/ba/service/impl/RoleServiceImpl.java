package io.khasang.ba.service.impl;

import io.khasang.ba.dao.RoleDao;
import io.khasang.ba.entity.OperatorRole;
import io.khasang.ba.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of RoleService based on DAO-layer utilization
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    /**
     * Add new role
     *
     * @param newOperatorRole New instance of role
     * @return Added {@link OperatorRole} instance
     */
    @Override
    public OperatorRole addRole(OperatorRole newOperatorRole) {
        return roleDao.add(newOperatorRole);
    }

    /**
     * Get role by id
     *
     * @param id Identifier of the desired role
     * @return Found {@link OperatorRole} instance
     */
    @Override
    public OperatorRole getRoleById(long id) {
        return roleDao.getById(id);
    }

    /**
     * Update existing role with new instance
     *
     * @param updatedOperatorRole Updated role instance
     * @return Updated {@link OperatorRole} instance
     */
    @Override
    public OperatorRole updateRole(OperatorRole updatedOperatorRole) {
        return roleDao.update(updatedOperatorRole);
    }

    /**
     * Get all roles
     *
     * @return {@link List} instance of all roles
     */
    @Override
    public List<OperatorRole> getAllRoles() {
        return roleDao.getAll();
    }

    /**
     * Delete role by id
     *
     * @param id Identifier of the role which should be deleted
     * @return Deleted {@link OperatorRole} instance
     */
    @Override
    public OperatorRole deleteRole(long id) {
        return roleDao.delete(getRoleById(id));
    }
}
