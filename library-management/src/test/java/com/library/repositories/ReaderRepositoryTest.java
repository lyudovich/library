package com.library.repositories;

import com.library.models.Book;
import com.library.models.Category;
import com.library.models.Loan;
import com.library.models.Reader;
import com.library.repositories.projections.ReaderView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReaderRepositoryTest {

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Test
    void findAllByDeletedFalse_returnsOnlyActiveReaders() {

        Reader active = createReader("Active Reader");
        Reader deleted = createReader("Deleted Reader");
        deleted.setDeleted(true);

        readerRepository.saveAllAndFlush(List.of(active, deleted));

        List<ReaderView> result =
                readerRepository.findAllByDeletedFalse();

        assertTrue(result.size() >= 1);

        assertTrue(
                result.stream().allMatch(r -> !r.getDeleted())
        );
        assertFalse(result.get(0).getDeleted());
        assertTrue(
                result.stream()
                        .anyMatch(r -> "Active Reader".equals(r.getFullName()))
        );

    }

    @Test
    void findTop3ReadersByBorrowCount_returnsOnlyActiveReadersWithLoans() {

        Category category = createCategory("Фантастика");

        Book book = createBook("Book", "ISBN-" + UUID.randomUUID(), category);

        Reader r1 = createReader("Reader 1");
        Reader r2 = createReader("Reader 2");
        Reader r3 = createReader("Reader 3");
        Reader r4 = createReader("Reader 4");

        readerRepository.saveAllAndFlush(List.of(r1, r2, r3, r4));

        createLoan(book, r1);
        createLoan(book, r1);
        createLoan(book, r1);

        createLoan(book, r2);
        createLoan(book, r2);

        createLoan(book, r3);

        List<ReaderView> result =
                readerRepository.findTop3ReadersByBorrowCount();

        assertNotNull(result);

        assertTrue(result.size() <= 3);

        assertTrue(
                result.stream().allMatch(r -> !r.getDeleted())
        );

        assertTrue(
                result.stream().allMatch(r ->
                        r.getFullName() != null && !r.getFullName().isBlank()
                                && r.getEmail() != null && !r.getEmail().isBlank()
                )
        );
    }


    private Reader createReader(String name) {
        Reader reader = new Reader();
        reader.setFullName(name);
        reader.setEmail(name.toLowerCase().replace(" ", "") + "@test.com");
        reader.setDeleted(false);
        return reader;
    }

    private Category createCategory(String name) {
        Category category = new Category();
        category.setName(name + "-" + UUID.randomUUID());
        return categoryRepository.saveAndFlush(category);
    }

    private Book createBook(String title, String isbn, Category category) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setCategory(category);
        return bookRepository.saveAndFlush(book);
    }

    private void createLoan(Book book, Reader reader) {
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setReader(reader);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14));
        loanRepository.saveAndFlush(loan);
    }
}
