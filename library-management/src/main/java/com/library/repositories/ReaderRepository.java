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
            WITH ReaderStats AS (
                SELECT 
                    r.id, 
                    r.full_name,
                    r.email,
                    r.is_deleted,
                    r.deleted_at,
                    r.updated_at,
                    COUNT(l.id) as total_loans,
                    DENSE_RANK() OVER (ORDER BY COUNT(l.id) DESC) as rank
                FROM readers r
                JOIN loans l ON r.id = l.reader_id
                JOIN books b ON l.book_id = b.id
                JOIN categories c ON b.category_id = c.id
                WHERE r.is_deleted = false
                GROUP BY r.id, r.full_name, r.email, r.is_deleted, r.deleted_at, r.updated_at
            )
            SELECT id, full_name, email, is_deleted, deleted_at, updated_at 
            FROM ReaderStats 
            WHERE rank <= 3
            """, nativeQuery = true)
    List<Reader> findTop3ReadersByBorrowCount();
}
//@Query(value = """
//    WITH ReaderStats AS (
//        SELECT
//            r.id,
//            r.full_name,
//            COUNT(l.id) as total_loans,
//            -- Віконна функція (Вимога ТЗ)
//            DENSE_RANK() OVER (ORDER BY COUNT(l.id) DESC) as rank
//        FROM readers r
//        -- JOIN 3+ таблиць (Вимога ТЗ)
//        JOIN loans l ON r.id = l.reader_id
//        JOIN books b ON l.book_id = b.id
//        JOIN categories c ON b.category_id = c.id
//        WHERE r.is_deleted = false
//        GROUP BY r.id, r.full_name
//    )
//    SELECT id, full_name, NULL as email, false as is_deleted, NULL as deleted_at, NULL as updated_at
//    FROM ReaderStats
//    WHERE rank <= 3
//    """, nativeQuery = true)
//List<Reader> findTop3ReadersByBorrowCount();