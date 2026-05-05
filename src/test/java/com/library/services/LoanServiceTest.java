package com.library.services;

import com.library.core.models.Book;
import com.library.core.models.Reader;
import com.library.core.repositories.BookRepository;
import com.library.core.repositories.LoanRepository;
import com.library.core.repositories.ReaderRepository;
import com.library.core.services.LoanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ReaderRepository readerRepository;

    @InjectMocks
    private LoanService loanService;

    @Test
    void issueBook_success() {
        Book book = new Book();
        book.setId(1L);

        Reader reader = new Reader();
        reader.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));
//        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        int loan = loanService.issueBook(1L, 1L);

        assertNotNull(loan);

        verify(bookRepository).findById(1L);
        verify(readerRepository).findById(1L);
    }

    @Test
    void issueBook_bookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> loanService.issueBook(1L, 1L)
        );

        assertEquals("Книгу не знайдено", exception.getMessage());
        verify(bookRepository).findById(1L);
        verify(readerRepository, never()).findById(any());
        verify(loanRepository, never()).save(any());
    }

    @Test
    void issueBook_readerNotFound() {
        Book book = new Book();
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(readerRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> loanService.issueBook(1L, 1L)
        );

        assertEquals("Читача не знайдено", exception.getMessage());
        verify(bookRepository).findById(1L);
        verify(readerRepository).findById(1L);
        verify(loanRepository, never()).save(any());
    }
}
