package com.library.repositories;

import com.library.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO loans (book_id, reader_id, loan_date, due_date, return_date)
        VALUES (:bookId, :readerId, :loanDate, :dueDate, :returnDate)
        """, nativeQuery = true)
    int insertLoan(
            @Param("bookId") Long bookId,
            @Param("readerId") Long readerId,
            @Param("loanDate") LocalDate loanDate,
            @Param("dueDate") LocalDate dueDate,
            @Param("returnDate") LocalDate returnDate
    );
}