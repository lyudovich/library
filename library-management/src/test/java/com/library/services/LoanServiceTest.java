package com.library.services;

import com.library.models.Book;
import com.library.models.Loan;
import com.library.models.Reader;
import com.library.repositories.BookRepository;
import com.library.repositories.LoanRepository;
import com.library.repositories.ReaderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Loan loan = loanService.issueBook(1L, 1L);

        assertNotNull(loan);
        assertEquals(book, loan.getBook());
        assertEquals(reader, loan.getReader());
        assertEquals(LocalDate.now(), loan.getLoanDate());
        assertEquals(LocalDate.now().plusDays(14), loan.getDueDate());

        verify(bookRepository).findById(1L);
        verify(readerRepository).findById(1L);
        verify(loanRepository).save(any(Loan.class));
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
