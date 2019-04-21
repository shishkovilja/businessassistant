package io.khasang.ba.service;

import io.khasang.ba.entity.OperatorRole;

import java.util.List;

/**
 * Service layer interface for role management
 */
public interface OperatorRoleService {

    /**
     * Add new operator's role
     *
     * @param newOperatorRole New instance of operator's role
     * @return Added {@link OperatorRole} instance
     */
    OperatorRole addOperatorRole(OperatorRole newOperatorRole);

    /**
     * Get role by id
     *
     * @param id Identifier of the desired role
     * @return Found {@link OperatorRole} instance
     */
    OperatorRole getOperatorRoleById(long id);

    /**
     * Update existing operator role with new instance
     *
     * @param updatedOperatorRole Updated role instance
     * @return Updated {@link OperatorRole} instance
     */
    OperatorRole updateOperatorRole(OperatorRole updatedOperatorRole);

    /**
     * Get all roles
     *
     * @return {@link List} instance of all roles
     */
    List<OperatorRole> getAllOperatorRoles();

    /**
     * Delete role by id
     *
     * @param id Identifier of the role which should be deleted
     * @return Deleted {@link OperatorRole} instance
     */
    OperatorRole deleteOperatorRole(long id);
}
