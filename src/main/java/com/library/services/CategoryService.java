package com.library.services;

import com.library.models.Category;
import com.library.repositories.CategoryRepository;
import com.library.userModel.UserCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<UserCategory> getAllCategories() {
        return categoryRepository.findAllBy().stream()
                .map(cv -> {
                    UserCategory uc = new UserCategory();
                    uc.setId(cv.getId());
                    uc.setName(cv.getName());
                    return uc;
                })
                .toList();
    }

    public Category createCategory(String name) {
        Category category = new Category(name);
        return categoryRepository.save(category);
    }
}