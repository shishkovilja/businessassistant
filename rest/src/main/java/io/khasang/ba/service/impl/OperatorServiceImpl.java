package io.khasang.ba.service.impl;

import io.khasang.ba.dao.OperatorDao;
import io.khasang.ba.entity.Operator;
import io.khasang.ba.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of OperatorService based on DAO-layer utilization
 */
@Service
public class OperatorServiceImpl implements OperatorService {

    @Autowired
    private OperatorDao operatorDao;

    /**
     * Add new Operator
     *
     * @param newOperator New instance of Operator
     * @return Added {@link Operator} instance
     */
    @Override
    public Operator addOperator(Operator newOperator) {
        newOperator.setRegistrationTimestamp(LocalDateTime.now());
        return operatorDao.add(newOperator);
    }

    /**
     * Get Operator by id
     *
     * @param id Identifier of the desired Operator
     * @return Found {@link Operator} instance
     */
    @Override
    public Operator getOperatorById(long id) {
        return operatorDao.getById(id);
    }

    /**
     * Update existing Operator with new instance
     *
     * @param updatedOperator Updated Operator instance
     * @return Updated {@link Operator} instance
     */
    @Override
    public Operator updateOperator(Operator updatedOperator) {
        return operatorDao.update(updatedOperator);
    }

    /**
     * Get all Operators
     *
     * @return {@link List} instance of all Operators
     */
    @Override
    public List<Operator> getAllOperators() {
        return operatorDao.getAll();
    }

    /**
     * Delete Operator by id
     *
     * @param id Identifier of the Operator which should be deleted
     * @return Deleted {@link Operator} instance
     */
    @Override
    public Operator deleteOperator(long id) {
        return operatorDao.delete(getOperatorById(id));
    }
}
