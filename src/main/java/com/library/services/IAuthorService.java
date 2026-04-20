package com.library.services;

import com.library.models.Author;
import com.library.projections.AuthorView;
import java.util.List;

public interface IAuthorService {
    List<AuthorView> getAllAuthors();
    Author createAuthor(Author author);
}