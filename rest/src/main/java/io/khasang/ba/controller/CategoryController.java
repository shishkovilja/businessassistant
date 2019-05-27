package io.khasang.ba.controller;

import io.khasang.ba.entity.Category;
import io.khasang.ba.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(value = "/add", produces = "application/json;charset=utf-8")
    public Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @GetMapping(value = "/get/{id}", produces = "application/json;charset=utf-8")
    public ResponseEntity<Category> getCategoryById(@PathVariable(value = "id") long id) {
        Category category = categoryService.getCategoryById(id);
        return category == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(category);
    }

    @PutMapping(value = "/update", produces = "application/json;charset=utf-8")
    public Category updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    @GetMapping(value = "/get/all", produces = "application/json;charset=utf-8")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/delete/{id}", produces = "application/json;charset=utf-8")
    public Category deleteCategory(@PathVariable(value = "id") long id) {
        return categoryService.deleteCategory(id);
    }
}
