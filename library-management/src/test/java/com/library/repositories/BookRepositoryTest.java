package com.library.repositories;

import com.library.models.Book;
import com.library.models.Category;
import com.library.repositories.projections.BookView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findByTitleContainingIgnoreCase_found() {

        Category category = new Category();
        category.setName("Роман");
        categoryRepository.saveAndFlush(category);

        Book book1 = createBook("Кобзар", "ISBN-001", category);
        Book book2 = createBook("Кобзар і поезії", "ISBN-002", category);
        Book book3 = createBook("Лісова пісня", "ISBN-003", category);

        bookRepository.saveAllAndFlush(List.of(book1, book2, book3));

        List<BookView> result =
                bookRepository.findByTitleContainingIgnoreCase("кобзар");

        assertNotNull(result);
        assertEquals(2, result.size());

        List<String> titles = result.stream()
                .map(BookView::getTitle)
                .toList();

        assertTrue(titles.contains("Кобзар"));
        assertTrue(titles.contains("Кобзар і поезії"));
        assertFalse(titles.contains("Лісова пісня"));
    }

    @Test
    void findByTitleContainingIgnoreCase_notFound() {

        Category category = new Category();
        category.setName("Фантастика-" + UUID.randomUUID());
        categoryRepository.saveAndFlush(category);

        Book book = createBook("Вій", "ISBN-100", category);
        bookRepository.saveAndFlush(book);

        List<BookView> result =
                bookRepository.findByTitleContainingIgnoreCase("Кобзар");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    private Book createBook(String title, String isbn, Category category) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setCategory(category);
        return book;
    }
}
