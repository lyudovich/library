package com.library.services;

import com.library.models.Author;
import com.library.projections.AuthorView;
import com.library.userModel.UserAuthor;
import java.util.List;

public interface IAuthorService {
    List<UserAuthor> getAllAuthors();
    Author createAuthor(Author author);
}