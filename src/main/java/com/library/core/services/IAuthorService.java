package com.library.core.services;

import com.library.core.models.Author;
import com.library.userModel.UserAuthor;
import java.util.List;

public interface IAuthorService {
    List<UserAuthor> getAllAuthors();
    Author createAuthor(Author author);
}
