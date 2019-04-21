package io.khasang.ba.service;

import io.khasang.ba.entity.OperatorRole;

import java.util.List;

/**
 * Service layer interface for role management
 */
public interface RoleService {

    /**
     * Add new role
     *
     * @param newOperatorRole New instance of role
     * @return Added {@link OperatorRole} instance
     */
    OperatorRole addRole(OperatorRole newOperatorRole);

    /**
     * Get role by id
     *
     * @param id Identifier of the desired role
     * @return Found {@link OperatorRole} instance
     */
    OperatorRole getRoleById(long id);

    /**
     * Update existing role with new instance
     *
     * @param updatedOperatorRole Updated role instance
     * @return Updated {@link OperatorRole} instance
     */
    OperatorRole updateRole(OperatorRole updatedOperatorRole);

    /**
     * Get all roles
     *
     * @return {@link List} instance of all roles
     */
    List<OperatorRole> getAllRoles();

    /**
     * Delete role by id
     *
     * @param id Identifier of the role which should be deleted
     * @return Deleted {@link OperatorRole} instance
     */
    OperatorRole deleteRole(long id);
}
