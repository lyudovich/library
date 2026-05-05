package com.library.core.repositories;

import com.library.core.models.Category;
import com.library.projections.CategoryView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<CategoryView> findAllBy();

}
