package com.library.services;

import com.library.models.Category;
import com.library.repositories.CategoryRepository;
import com.library.repositories.projections.CategoryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryView> getAllCategories() {
        return categoryRepository.findAllBy();
    }

    public Category createCategory(String name) {
        Category category = new Category(name);
        return categoryRepository.save(category);
    }
}
