package io.khasang.ba.dao.impl;

import io.khasang.ba.dao.RoleDao;
import io.khasang.ba.entity.OperatorRole;

public class RoleDaoImpl extends BasicDaoImpl<OperatorRole> implements RoleDao {
    public RoleDaoImpl(Class<OperatorRole> entityClass) {
        super(entityClass);
    }
}
