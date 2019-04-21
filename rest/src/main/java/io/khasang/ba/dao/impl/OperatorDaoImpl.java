package io.khasang.ba.dao.impl;

import io.khasang.ba.dao.OperatorDao;
import io.khasang.ba.entity.Operator;

public class OperatorDaoImpl extends BasicDaoImpl<Operator> implements OperatorDao {
    public OperatorDaoImpl(Class<Operator> entityClass) {
        super(entityClass);
    }
}
