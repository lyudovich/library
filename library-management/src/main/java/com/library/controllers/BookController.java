package com.library.controllers;

import com.library.models.Book;
import com.library.repositories.projections.BookView;
import com.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookView> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}/title")
    public Book updateBookTitle(
            @PathVariable Long id,
            @RequestParam String newTitle
    ) {
        return bookService.updateBookTitle(id, newTitle);
    }
}
