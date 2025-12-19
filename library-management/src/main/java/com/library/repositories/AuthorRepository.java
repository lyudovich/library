package com.library.repositories;

import com.library.models.Author;
import com.library.repositories.projections.AuthorView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<AuthorView> findAllBy();

}
