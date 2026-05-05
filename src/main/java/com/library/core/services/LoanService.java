package com.library.core.services;

import com.library.core.models.Book;
import com.library.core.models.Loan;
import com.library.core.models.Reader;
import com.library.core.repositories.BookRepository;
import com.library.core.repositories.LoanRepository;
import com.library.core.repositories.ReaderRepository;
import com.library.core.models.*;
import com.library.core.repositories.*;
import com.library.userModel.UserLoan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService implements ILoanService {

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

        return loanRepository.insertLoan(loan.getBook().getId(), loan.getReader().getId(), loan.getLoanDate(),
                loan.getDueDate(), loan.getReturnDate());
    }

    @Transactional(readOnly = true)
    public List<UserLoan> getAllLoans() {
        return loanRepository.findAllBy().stream()
                .map(lv -> {
                    UserLoan ul = new UserLoan();
                    ul.setId(lv.getId());
                    ul.setLoanDate(lv.getLoanDate());
                    ul.setDueDate(lv.getDueDate());
                    ul.setReturnDate(lv.getReturnDate());
                    ul.setBookId(lv.getBook() != null ? lv.getBook().getId() : null);
                    ul.setBookTitle(lv.getBook() != null ? lv.getBook().getTitle() : null);
                    ul.setBookIsbn(lv.getBook() != null ? lv.getBook().getIsbn() : null);
                    ul.setReaderId(lv.getReader() != null ? lv.getReader().getId() : null);
                    ul.setReaderFullName(lv.getReader() != null ? lv.getReader().getFullName() : null);
                    ul.setReaderEmail(lv.getReader() != null ? lv.getReader().getEmail() : null);
                    return ul;
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserLoan> getActiveLoans() {
        return loanRepository.findAllByReturnDateIsNull().stream()
                .map(lv -> {
                    UserLoan ul = new UserLoan();
                    ul.setId(lv.getId());
                    ul.setLoanDate(lv.getLoanDate());
                    ul.setDueDate(lv.getDueDate());
                    ul.setReturnDate(lv.getReturnDate());
                    ul.setBookId(lv.getBook() != null ? lv.getBook().getId() : null);
                    ul.setBookTitle(lv.getBook() != null ? lv.getBook().getTitle() : null);
                    ul.setBookIsbn(lv.getBook() != null ? lv.getBook().getIsbn() : null);
                    ul.setReaderId(lv.getReader() != null ? lv.getReader().getId() : null);
                    ul.setReaderFullName(lv.getReader() != null ? lv.getReader().getFullName() : null);
                    ul.setReaderEmail(lv.getReader() != null ? lv.getReader().getEmail() : null);
                    return ul;
                })
                .toList();
    }

    @Transactional
    public void deleteLoan(Long loanId) {
        if (!loanRepository.existsById(loanId)) {
            throw new RuntimeException("Позика не знайдена");
        }
        loanRepository.deleteById(loanId);
    }
}
