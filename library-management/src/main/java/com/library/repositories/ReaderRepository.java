package com.library.repositories;

import com.library.models.Reader;
import com.library.repositories.projections.ReaderView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {

    List<ReaderView> findAllBy();

    List<ReaderView> findAllByDeletedFalse();

    @Query(value = """
        SELECT 
            r.id           AS id,
            r.full_name    AS fullName,
            r.email        AS email,
            r.is_deleted   AS deleted,
            r.deleted_at   AS deletedAt,
            r.updated_at   AS updatedAt
        FROM readers r
        JOIN loans l ON r.id = l.reader_id
        WHERE r.is_deleted = false
        GROUP BY r.id, r.full_name, r.email, r.is_deleted, r.deleted_at, r.updated_at
        ORDER BY COUNT(l.id) DESC
        LIMIT 3
        """, nativeQuery = true)
    List<ReaderView> findTop3ReadersByBorrowCount();
}

