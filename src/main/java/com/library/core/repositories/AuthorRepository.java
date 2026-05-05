package com.library.core.repositories;

import com.library.core.models.Author;
import com.library.projections.AuthorView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<AuthorView> findAllBy();

}
