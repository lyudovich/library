package com.library.services;

import com.library.models.Book;
import com.library.repositories.BookRepository;
import com.library.repositories.projections.BookView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public Book createBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new RuntimeException("Назва книги не може бути порожньою");
        }
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public List<BookView> getAllBooks() {
        return bookRepository.findAllBy();
    }

    @Transactional
    public Book updateBookTitle(Long id, String newTitle) {
        int updated = bookRepository.updateBookTitleNative(id, newTitle);

        if (updated == 0) {
            throw new RuntimeException("Книгу з ID " + id + " не знайдено");
        }
        return null;
    }


    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<BookView> findByTitleContainingIgnoreCase(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
}