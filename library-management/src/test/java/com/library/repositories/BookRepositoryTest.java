package com.library.repositories;

import com.library.models.Book;
import com.library.models.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

//    @Test
//    void findByTitleContainingIgnoreCase_found() {
//        Category category = new Category();
//        category.setName("Роман");
//        categoryRepository.save(category);
//
//        Book book1 = new Book();
//        book1.setTitle("Кобзар");
//        book1.setCategory(category);
//
//        Book book2 = new Book();
//        book2.setTitle("Кобзар і поезії");
//        book2.setCategory(category);
//
//        Book book3 = new Book();
//        book3.setTitle("Лісова пісня");
//        book3.setCategory(category);
//
//        bookRepository.saveAll(List.of(book1, book2, book3));
//
//        List<Book> result = bookRepository.findByTitleContainingIgnoreCase("кобзар");
//
//        assertEquals(2, result.size());
//
//        List<String> titles = result.stream()
//                .map(Book::getTitle)
//                .toList();
//
//        assertTrue(titles.contains("Кобзар"));
//        assertTrue(titles.contains("Кобзар і поезії"));
//        assertFalse(titles.contains("Лісова пісня"));
//    }
//
//    @Test
//    void findByTitleContainingIgnoreCase_notFound() {
//        Category category = new Category();
//        category.setName("Фантастика");
//        categoryRepository.save(category);
//
//        Book book = new Book();
//        book.setTitle("Вій");
//        book.setCategory(category);
//        bookRepository.save(book);
//
//        List<Book> result = bookRepository.findByTitleContainingIgnoreCase("Кобзар");
//
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
}
