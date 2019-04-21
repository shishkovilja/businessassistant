package io.khasang.ba.service.impl;

import io.khasang.ba.dao.OperatorRoleDao;
import io.khasang.ba.entity.OperatorRole;
import io.khasang.ba.service.OperatorRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of OperatorRoleService based on DAO-layer utilization
 */
@Service
public class OperatorRoleServiceImpl implements OperatorRoleService {

    @Autowired
    private OperatorRoleDao operatorRoleDao;

    /**
     * Add new role
     *
     * @param newOperatorRole New instance of  operator's role
     * @return Added {@link OperatorRole} instance
     */
    @Override
    public OperatorRole addOperatorRole(OperatorRole newOperatorRole) {
        return operatorRoleDao.add(newOperatorRole);
    }

    /**
     * Get role by id
     *
     * @param id Identifier of the desired operator's role
     * @return Found {@link OperatorRole} instance
     */
    @Override
    public OperatorRole getOperatorRoleById(long id) {
        return operatorRoleDao.getById(id);
    }

    /**
     * Update existing operator's role with new instance
     *
     * @param updatedOperatorRole Updated operator's role instance
     * @return Updated {@link OperatorRole} instance
     */
    @Override
    public OperatorRole updateOperatorRole(OperatorRole updatedOperatorRole) {
        return operatorRoleDao.update(updatedOperatorRole);
    }

    /**
     * Get all roles
     *
     * @return {@link List} instance of all roles
     */
    @Override
    public List<OperatorRole> getAllOperatorRoles() {
        return operatorRoleDao.getAll();
    }

    /**
     * Delete operator's role by id
     *
     * @param id Identifier of the role which should be deleted
     * @return Deleted {@link OperatorRole} instance
     */
    @Override
    public OperatorRole deleteOperatorRole(long id) {
        return operatorRoleDao.delete(getOperatorRoleById(id));
    }
}
