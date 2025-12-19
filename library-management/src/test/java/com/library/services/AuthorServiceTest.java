package com.library.services;

import com.library.models.Author;
import com.library.repositories.AuthorRepository;
import com.library.repositories.projections.AuthorView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void getAllAuthors_success() {
        AuthorView author1 = mock(AuthorView.class);
        AuthorView author2 = mock(AuthorView.class);

        List<AuthorView> authors = List.of(author1, author2);
        when(authorRepository.findAllBy()).thenReturn(authors);

        List<AuthorView> result = authorService.getAllAuthors();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(authorRepository).findAllBy();
    }




    @Test
    void createAuthor_success() {
        Author author = new Author();
        author.setName("Тарас Шевченко");

        when(authorRepository.save(author)).thenReturn(author);

        Author result = authorService.createAuthor(author);

        assertNotNull(result);
        assertEquals("Тарас Шевченко", result.getName());
        verify(authorRepository).save(author);
    }

    @Test
    void createAuthor_nameIsNull() {
        Author author = new Author();
        author.setName(null);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authorService.createAuthor(author)
        );

        assertEquals("Ім'я автора не може бути порожнім", exception.getMessage());
        verify(authorRepository, never()).save(any());
    }

    @Test
    void createAuthor_nameIsEmpty() {
        Author author = new Author();
        author.setName("");

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authorService.createAuthor(author)
        );

        assertEquals("Ім'я автора не може бути порожнім", exception.getMessage());
        verify(authorRepository, never()).save(any());
    }
}
