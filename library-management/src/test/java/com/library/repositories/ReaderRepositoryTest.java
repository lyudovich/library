package com.library.repositories;

import com.library.models.Book;
import com.library.models.Category;
import com.library.models.Loan;
import com.library.models.Reader;
import com.library.repositories.projections.ReaderView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
@SpringBootTest
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
        Reader active = new Reader();
        active.setFullName("Active Reader");
        active.setDeleted(false);

        Reader deleted = new Reader();
        deleted.setFullName("Deleted Reader");
        deleted.setDeleted(true);

        readerRepository.save(active);
        readerRepository.save(deleted);

        List<ReaderView> result = readerRepository.findAllByDeletedFalse();

        assertEquals(1, result.size());
        assertFalse(result.get(0).getDeleted());
        assertEquals("Active Reader", result.get(0).getFullName());
    }


//    @Test
//    void findTop3ReadersByBorrowCount_returnsTopReaders() {
////        Category category = new Category();
////        category.setName("Фантастика");
////        categoryRepository.save(category);
//
//        Book book = new Book();
//        book.setTitle("Book");
//        book.setIsbn("123456789");
////        book.setCategory(category);
//        bookRepository.save(book);
//
//        Reader r1 = createReader("Reader 1");
//        Reader r2 = createReader("Reader 2");
//        Reader r3 = createReader("Reader 3");
//        Reader r4 = createReader("Reader 4");
//
//        readerRepository.saveAll(List.of(r1, r2, r3, r4));
//
//        createLoan(book, r1);
//        createLoan(book, r1);
//        createLoan(book, r1);
//
//        createLoan(book, r2);
//        createLoan(book, r2);
//
//        createLoan(book, r3);
//
//        List<ReaderView> topReaders = readerRepository.findTop3ReadersByBorrowCount();
//
//        assertEquals(3, topReaders.size());
//
//        List<String> names = topReaders.stream()
//                .map(ReaderView::getFullName)
//                .toList();
//
//        assertTrue(names.contains("Reader 1"));
//        assertTrue(names.contains("Reader 2"));
//        assertTrue(names.contains("Reader 3"));
//        assertFalse(names.contains("Reader 4"));
//    }
//
//
//    private Reader createReader(String name) {
//        Reader reader = new Reader();
//        reader.setFullName(name);
//        reader.setDeleted(false);
//        return reader;
//    }
//
//    private void createLoan(Book book, Reader reader) {
//        Loan loan = new Loan();
//        loan.setBook(book);
//        loan.setReader(reader);
//        loan.setLoanDate(LocalDate.now());
//        loan.setDueDate(LocalDate.now().plusDays(14));
//        loanRepository.save(loan);
//    }
//
//    @Test
//    void findAllActiveReaders() {
//        List<ReaderView> result = null;
//        result = readerRepository.findAllActiveReaders();
//        assertNotNull(result);
//    }
}
