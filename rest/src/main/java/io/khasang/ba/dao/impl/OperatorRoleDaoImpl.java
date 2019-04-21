package io.khasang.ba.dao.impl;

import io.khasang.ba.dao.OperatorRoleDao;
import io.khasang.ba.entity.OperatorRole;

public class OperatorRoleDaoImpl extends BasicDaoImpl<OperatorRole> implements OperatorRoleDao {
    public OperatorRoleDaoImpl(Class<OperatorRole> entityClass) {
        super(entityClass);
    }
}
