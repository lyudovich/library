package com.library.services;

import com.library.models.*;
import com.library.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
public class LoanService {
    @Autowired private LoanRepository loanRepository;
    @Autowired private BookRepository bookRepository;
    @Autowired private ReaderRepository readerRepository;

    @Transactional
    public int issueBook(Long bookId, Long readerId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Книгу не знайдено"));

        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(() -> new RuntimeException("Читача не знайдено"));

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setReader(reader);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14));

//        return loanRepository.save(loan);
        return loanRepository.insertLoan(loan.getBook().getId(), loan.getReader().getId(), loan.getLoanDate(),
                loan.getDueDate(), loan.getReturnDate());
    }
}