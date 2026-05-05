package com.library.services;

import com.library.core.models.Book;
import com.library.core.models.BookCreatedEvent;
import com.library.core.repositories.BookRepository;
import com.library.core.services.AuditService;
import com.library.core.services.BookService;
import com.library.core.services.event.EventBus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Mock
    private AuditService auditService;

    @Mock
    private EventBus eventBus;

    @Test
    void updateBookTitle_success() {

        when(bookRepository.updateBookTitleNative(1L, "Нова назва"))
                .thenReturn(1);

        assertDoesNotThrow(() ->
                bookService.updateBookTitle(1L, "Нова назва")
        );

        verify(bookRepository).updateBookTitleNative(1L, "Нова назва");
        verifyNoMoreInteractions(bookRepository);
    }


    @Test
    void updateBookTitle_bookNotFound() {

        when(bookRepository.updateBookTitleNative(99L, "Нова назва"))
                .thenReturn(0); // нічого не оновлено

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> bookService.updateBookTitle(99L, "Нова назва")
        );

        assertEquals("Книгу з ID 99 не знайдено", ex.getMessage());

        verify(bookRepository).updateBookTitleNative(99L, "Нова назва");
        verifyNoMoreInteractions(bookRepository);
    }


    @Test
    void getBookById_found() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());

        verify(bookRepository).findById(1L);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void getBookById_notFound() {
        when(bookRepository.findById(2L))
                .thenReturn(Optional.empty());

        Book result = bookService.getBookById(2L);

        assertNull(result);

        verify(bookRepository).findById(2L);
        verifyNoMoreInteractions(bookRepository);
    }


    @Test
    void createBook_success() {
        Book toCreate = new Book();
        toCreate.setTitle("Місто");
        toCreate.setIsbn("978-617-12-3456-7");

        when(bookRepository.save(any(Book.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Book created = bookService.createBook(toCreate);

        assertNotNull(created);
        assertEquals("Місто", created.getTitle());

        verify(bookRepository).save(toCreate);

        verify(auditService).log("BOOK_CREATED_SYNC", "Місто");
        verify(eventBus).publish(any(BookCreatedEvent.class));

        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void createBook_titleIsNull_throws() {
        Book toCreate = new Book();
        toCreate.setTitle(null);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> bookService.createBook(toCreate)
        );

        assertEquals("Назва книги не може бути порожньою", ex.getMessage());

        verifyNoInteractions(bookRepository);
    }

    @Test
    void createBook_titleIsBlank_throws() {
        Book toCreate = new Book();
        toCreate.setTitle("   ");

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> bookService.createBook(toCreate)
        );

        assertEquals("Назва книги не може бути порожньою", ex.getMessage());

        verifyNoInteractions(bookRepository);
    }

    @Test
    void createBook_publishesEvent() {
        Book toCreate = new Book();
        toCreate.setTitle("Test");

        when(bookRepository.save(any(Book.class)))
                .thenAnswer(inv -> {
                    Book b = inv.getArgument(0);
                    b.setId(1L);
                    return b;
                });

        ArgumentCaptor<BookCreatedEvent> captor =
                ArgumentCaptor.forClass(BookCreatedEvent.class);

        bookService.createBook(toCreate);

        verify(eventBus).publish(captor.capture());

        BookCreatedEvent event = captor.getValue();

        assertEquals(1L, event.bookId);
        assertEquals("Test", event.title);
    }

    @Test
    void createBook_auditFails_butBookStillCreated() {
        Book toCreate = new Book();
        toCreate.setTitle("Test");

        when(bookRepository.save(any(Book.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        doThrow(new RuntimeException("Audit error"))
                .when(auditService).log(any(), any());

        assertDoesNotThrow(() ->
                bookService.createBook(toCreate)
        );

        verify(bookRepository).save(toCreate);
        verify(eventBus).publish(any(BookCreatedEvent.class));
    }
}

