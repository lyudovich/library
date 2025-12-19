package com.library.repositories;

import com.library.models.Book;
import com.library.repositories.projections.BookView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<BookView> findByTitleContainingIgnoreCase(@Param("title") String title);


    List<BookView> findAllBy();

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE books
        SET title = :newTitle
        WHERE id = :id
        """, nativeQuery = true)
    int updateBookTitleNative(
            @Param("id") Long id,
            @Param("newTitle") String newTitle
    );
}