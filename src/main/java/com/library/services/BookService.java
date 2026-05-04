package com.library.services;

import com.library.models.Book;
import com.library.projections.BookView;
import com.library.userModel.UserBook;

import java.util.List;

public interface BookService {
//    Book createBook();
    List<UserBook> getAllBooks();
}
