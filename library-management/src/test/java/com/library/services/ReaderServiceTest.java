package com.library.services;

import com.library.models.Reader;
import com.library.repositories.ReaderRepository;
import com.library.repositories.projections.ReaderView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReaderServiceTest {

    @Mock
    private ReaderRepository readerRepository;

    @InjectMocks
    private ReaderService readerService;

    @Test
    void softDeleteReader_success() {

        when(readerRepository.softDeleteById(1L))
                .thenReturn(1);

        assertDoesNotThrow(() ->
                readerService.softDeleteReader(1L)
        );

        verify(readerRepository).softDeleteById(1L);
        verifyNoMoreInteractions(readerRepository);
    }

    @Test
    void softDeleteReader_notFound() {

        when(readerRepository.softDeleteById(1L))
                .thenReturn(0);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> readerService.softDeleteReader(1L)
        );

        assertEquals("Читача не знайдено", ex.getMessage());

        verify(readerRepository).softDeleteById(1L);
        verifyNoMoreInteractions(readerRepository);
    }

    @Test
    void hardDeleteReader_success() {

        when(readerRepository.hardDeleteById(1L))
                .thenReturn(1);

        assertDoesNotThrow(() ->
                readerService.hardDeleteReader(1L)
        );

        verify(readerRepository).hardDeleteById(1L);
        verifyNoMoreInteractions(readerRepository);
    }

    @Test
    void hardDeleteReader_notFound() {

        when(readerRepository.hardDeleteById(1L))
                .thenReturn(0);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> readerService.hardDeleteReader(1L)
        );

        assertEquals("Читача з ID 1 не знайдено", ex.getMessage());

        verify(readerRepository).hardDeleteById(1L);
        verifyNoMoreInteractions(readerRepository);
    }

    @Test
    void createReader_success() {

        Reader reader = new Reader();

        readerService.create(reader);

        verify(readerRepository).save(reader);
        verifyNoMoreInteractions(readerRepository);
    }


    @Test
    void getAllReaders_success() {

        ReaderView r1 = mock(ReaderView.class);
        ReaderView r2 = mock(ReaderView.class);

        when(readerRepository.findAllBy())
                .thenReturn(List.of(r1, r2));

        List<ReaderView> result =
                readerService.getAllReaders();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(readerRepository).findAllBy();
        verifyNoMoreInteractions(readerRepository);
    }
}
