package com.library.services;

import com.library.models.Category;
import com.library.projections.CategoryView;
import java.util.List;

public interface ICategoryService {
    List<CategoryView> getAllCategories();
    Category createCategory(String name);
}