package io.khasang.ba.controller;

import io.khasang.ba.entity.Category;
import io.khasang.ba.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/get/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Category> getAll() {
        return categoryService.getAllCategories();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Category getCategory(@PathVariable(value = "id") long id) {
        return categoryService.getCategoryById(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Category updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Category deleteCategory(@PathVariable(value = "id") long id) {
        return categoryService.deleteCategory(id);
    }
}
