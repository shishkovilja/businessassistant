package io.khasang.ba.dao.impl;

import io.khasang.ba.dao.CategoryDao;
import io.khasang.ba.entity.Category;

public class CategoryDaoImpl extends BasicDaoImpl<Category> implements CategoryDao {
    public CategoryDaoImpl(Class<Category> entityClass) {
        super(entityClass);
    }
}
