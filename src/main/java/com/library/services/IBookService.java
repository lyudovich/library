package com.library.services;

import com.library.models.Book;
import com.library.projections.BookView;
import java.util.List;

public interface IBookService {
    Book createBook(Book book);
    List<BookView> getAllBooks();
    Book updateBookTitle(Long id, String newTitle);
    Book getBookById(Long id);
    List<BookView> findByTitleContainingIgnoreCase(String title);
}