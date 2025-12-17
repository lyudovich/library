package com.library.repositories;

import com.library.models.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class BookRepositoryTest {
    @Autowired BookRepository bookRepository;
    @Test
    void findByTitleContainingIgnoreCase() {
      List <Book> books = bookRepository.findByTitleContainingIgnoreCase("1984");
      assertNotNull(books);
    }

}