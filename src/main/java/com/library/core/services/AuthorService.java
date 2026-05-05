package com.library.core.services;

import com.library.core.models.Author;
import com.library.core.repositories.AuthorRepository;
import com.library.userModel.UserAuthor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AuthorService implements IAuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    public List<UserAuthor> getAllAuthors() {
        return authorRepository.findAllBy().stream()
                .map(av -> {
                    UserAuthor ua = new UserAuthor();
                    ua.setId(av.getId());
                    ua.setName(av.getName());
                    return ua;
                })
                .toList();
    }

    @Transactional
    public Author createAuthor(Author author) {
        if (author.getName() == null || author.getName().isEmpty()) {
            throw new RuntimeException("Ім'я автора не може бути порожнім");
        }
        return authorRepository.save(author);
    }
}
