package com.library.controllers;

import com.library.core.models.Book;
import com.library.projections.BookView;
import com.library.security.SecurityUtil;
import com.library.core.services.IBookService;
import com.library.userModel.UserBook;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private IBookService bookService;

    @GetMapping
    public List<UserBook> getAllBooks(HttpServletRequest request) {
        SecurityUtil.requireAdmin(request);
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}/updtitle")
    public Book updateBookTitle(
            @PathVariable Long id,
            @RequestParam String newTitle
    ) {
        return bookService.updateBookTitle(id, newTitle);
    }

    @GetMapping("/ftitle")
    public List<BookView> getBookByTitle(@RequestParam String title) {
        return bookService.findByTitleContainingIgnoreCase(title);
    }

    @PostMapping
    public Book addBook(@RequestBody Book book, HttpServletRequest request) {
        SecurityUtil.requireAdmin(request);
        return bookService.createBook(book);
    }
}
