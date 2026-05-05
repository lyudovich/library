package com.library.library_management;

import com.library.core.models.Book;
import com.library.core.models.Category;
import com.library.core.models.Loan;
import com.library.core.models.Reader;
import com.library.core.repositories.BookRepository;
import com.library.core.repositories.CategoryRepository;
import com.library.core.repositories.LoanRepository;
import com.library.core.repositories.ReaderRepository;
import com.library.projections.ReaderView;
import com.library.core.services.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryManagementTest {

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    void testAnalyticalTopReadersQuery() {

        Category category = new Category();
        category.setName("Test Category");
        categoryRepository.save(category);

        Book book = new Book();
        book.setTitle("Test Book");
        book.setIsbn("TEST-ISBN-001");
        book.setCategory(category);
        bookRepository.save(book);

        Reader r1 = createReader("Reader 1");
        Reader r2 = createReader("Reader 2");
        Reader r3 = createReader("Reader 3");
        Reader r4 = createReader("Reader 4");

        readerRepository.saveAll(List.of(r1, r2, r3, r4));

        createLoan(book, r1);
        createLoan(book, r1);
        createLoan(book, r1);

        createLoan(book, r2);
        createLoan(book, r2);

        createLoan(book, r3);

        List<ReaderView> topReaders =
                readerRepository.findTop3ReadersByBorrowCount();

        assertNotNull(topReaders);
        assertEquals(3, topReaders.size());

        List<String> names = topReaders.stream()
                .map(ReaderView::getFullName)
                .toList();

        assertFalse(names.contains("Reader 4"));
    }


    @Test
    void testTransactionRollbackOnFailure() {

        long initialCount = loanRepository.count();

        // readerId = 999 does not exist -> RuntimeException -> rollback
        assertThrows(RuntimeException.class, () -> {
            loanService.issueBook(1L, 999L);
        });

        long finalCount = loanRepository.count();

        assertEquals(
                initialCount,
                finalCount,
                "Транзакція повинна була відкотитися, кількість записів не має змінитись"
        );
    }

    private Reader createReader(String fullName) {
        Reader reader = new Reader();
        reader.setFullName(fullName);
        reader.setEmail(fullName.toLowerCase().replace(" ", ".") + "@test.com");
        reader.setDeleted(false);
        return reader;
    }

    private void createLoan(Book book, Reader reader) {
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setReader(reader);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14));
        loanRepository.save(loan);
    }
}
