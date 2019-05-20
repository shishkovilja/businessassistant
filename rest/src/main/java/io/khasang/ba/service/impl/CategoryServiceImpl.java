package io.khasang.ba.service.impl;

import io.khasang.ba.dao.CategoryDao;
import io.khasang.ba.entity.Category;
import io.khasang.ba.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public Category addCategory(Category category) {
        return categoryDao.add(category);
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryDao.getById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAll();
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryDao.update(category);
    }

    @Override
    public Category deleteCategory(long id) {
        return categoryDao.delete(this.getCategoryById(id));
    }
}
