package com.library.services;

import com.library.models.Book;
import com.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public Book updateBookTitle(Long id, String newTitle) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книгу з ID " + id + " не знайдено"));

        book.setTitle(newTitle);
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
}