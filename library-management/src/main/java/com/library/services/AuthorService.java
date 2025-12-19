package com.library.services;

import com.library.models.Author;
import com.library.repositories.AuthorRepository;
import com.library.repositories.projections.AuthorView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    public List<AuthorView> getAllAuthors() {
        return authorRepository.findAllBy();
    }


    @Transactional
    public Author createAuthor(Author author) {
        if (author.getName() == null || author.getName().isEmpty()) {
            throw new RuntimeException("Ім'я автора не може бути порожнім");
        }
        return authorRepository.save(author);
    }
}