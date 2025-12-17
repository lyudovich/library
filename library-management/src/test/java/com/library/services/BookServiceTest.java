package com.library.services;

import com.library.models.Book;
import com.library.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void updateBookTitle_success() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Стара назва");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book updatedBook = bookService.updateBookTitle(1L, "Нова назва");

        assertNotNull(updatedBook);
        assertEquals("Нова назва", updatedBook.getTitle());

        verify(bookRepository).findById(1L);
        verify(bookRepository).save(book);
    }

    @Test
    void updateBookTitle_bookNotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> bookService.updateBookTitle(99L, "Нова назва")
        );

        assertEquals("Книгу з ID 99 не знайдено", exception.getMessage());
        verify(bookRepository).findById(99L);
        verify(bookRepository, never()).save(any());
    }

    @Test
    void getBookById_found() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Book", result.getTitle());
    }

    @Test
    void getBookById_notFound() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        Book result = bookService.getBookById(2L);

        assertNull(result);
    }
}
