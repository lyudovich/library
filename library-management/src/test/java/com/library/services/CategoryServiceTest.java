package com.library.services;

import com.library.models.Category;
import com.library.repositories.CategoryRepository;
import com.library.repositories.projections.CategoryView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void getAllCategories_success() {
        CategoryView category1 = mock(CategoryView.class);
        CategoryView category2 = mock(CategoryView.class);

        when(categoryRepository.findAllBy())
                .thenReturn(List.of(category1, category2));

        List<CategoryView> result = categoryService.getAllCategories();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(categoryRepository).findAllBy();
    }


    @Test
    void createCategory_success() {
        when(categoryRepository.save(any(Category.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Category result = categoryService.createCategory("Історія");

        assertNotNull(result);
        assertEquals("Історія", result.getName());
        verify(categoryRepository).save(any(Category.class));
    }
}
