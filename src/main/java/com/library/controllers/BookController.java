package com.library.controllers;

import com.library.models.Book;
import com.library.projections.BookView;
import com.library.security.SecurityUtil;
import com.library.services.BookServiceImp;
import com.library.userModel.UserBook;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookServiceImp bookServiceImp;

    @GetMapping
    public List<UserBook> getAllBooks(HttpServletRequest request) {
        SecurityUtil.requireAdmin(request);
        return bookServiceImp.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookServiceImp.getBookById(id);
    }

    @PutMapping("/{id}/updtitle")
    public Book updateBookTitle(
            @PathVariable Long id,
            @RequestParam String newTitle
    ) {
        return bookServiceImp.updateBookTitle(id, newTitle);
    }

    @GetMapping("/ftitle")
    public List<BookView> getBookByTitle(@RequestParam String title) {
        return bookServiceImp.findByTitleContainingIgnoreCase(title);
    }

    @PostMapping
    public Book addBook(@RequestBody Book book, HttpServletRequest request) {
        SecurityUtil.requireAdmin(request);
        return bookServiceImp.createBook(book);
    }

}
