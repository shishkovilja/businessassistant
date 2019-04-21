package io.khasang.ba.service;

import io.khasang.ba.entity.Operator;

import java.util.List;

/**
 * Service layer interface for Operator management
 */
public interface OperatorService {

    /**
     * Add new Operator
     *
     * @param newOperator New instance of Operator
     * @return Added {@link Operator} instance
     */
    Operator addOperator(Operator newOperator);

    /**
     * Get Operator by id
     *
     * @param id Identifier of the desired Operator
     * @return Found {@link Operator} instance
     */
    Operator getOperatorById(long id);

    /**
     * Update existing Operator with new instance
     *
     * @param updatedOperator Updated Operator instance
     * @return Updated {@link Operator} instance
     */
    Operator updateOperator(Operator updatedOperator);

    /**
     * Get all Operators
     *
     * @return {@link List} instance of all Operators
     */
    List<Operator> getAllOperators();

    /**
     * Delete Operator by id
     *
     * @param id Identifier of the Operator which should be deleted
     * @return Deleted {@link Operator} instance
     */
    Operator deleteOperator(long id);
}
