package com.library.services;

import com.library.models.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AuthorServiceTest {
@Autowired AuthorService authorService;
    @Test
    void getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        assertNotNull(authors);
    }

    @Test
    void createAuthor() {
        Author author = new Author();
        author.setName("John Doe");
        authorService.createAuthor(author);

    }
}