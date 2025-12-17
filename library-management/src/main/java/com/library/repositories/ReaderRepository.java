package com.library.repositories;

import com.library.models.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {

    @Query("SELECT r FROM Reader r WHERE r.isDeleted = false")
    List<Reader> findAllActiveReaders();
    @Query(value = """
            SELECT r.* FROM readers r
            JOIN borrowings b ON r.id = b.reader_id
            GROUP BY r.id
            ORDER BY COUNT(b.id) DESC
            LIMIT 3
            """, nativeQuery = true)
    List<Reader> findTop3ReadersByBorrowCount();
}