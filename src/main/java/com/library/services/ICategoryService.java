package com.library.services;

import com.library.models.Category;
import com.library.userModel.UserCategory;
import java.util.List;

public interface ICategoryService {
    List<UserCategory> getAllCategories();
    Category createCategory(String name);
}