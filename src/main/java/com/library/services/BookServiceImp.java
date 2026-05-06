package com.library.services;

import com.library.models.Book;
import com.library.repositories.BookRepository;
import com.library.projections.BookView;
import com.library.userModel.UserBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImp implements BookService {

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
    public List<UserBook> getAllBooks() {

        List<Book> books = bookRepository.findAllBy();

        List<UserBook> bookViews = new ArrayList<>();

        for (Book book : books) {
            UserBook userBook = new UserBook();
            userBook.setId(book.getId());
            userBook.setTitle(book.getTitle());
            userBook.setIsbn(book.getIsbn());
            userBook.setCategory(book.getCategory().getName());
            bookViews.add(userBook);

        }


        return bookViews;
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
