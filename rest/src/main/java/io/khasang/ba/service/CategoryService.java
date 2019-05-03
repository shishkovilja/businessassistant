package io.khasang.ba.service;

import io.khasang.ba.entity.Category;

import java.util.List;

public interface CategoryService {
    /**
     * method for add Category
     *
     * @param category = category for adding
     * @return created category
     */
    Category addCategory(Category category);

    /**
     * method for getting category by specific id
     *
     * @param id - category
     * @return category by id
     */
    Category getCategoryById(long id);

    /**
     * method gor getting all categories
     *
     * @return all categories
     */
    List<Category> getAllCategories();

    /**
     * method for update category
     *
     * @param category - category update
     * @return updated category
     */
    Category updateCategory(Category category);

    /**
     * method for delete category by id
     *
     * @param id - category id for delete
     * @return deleted category
     */
    Category deleteCategory(long id);
}
