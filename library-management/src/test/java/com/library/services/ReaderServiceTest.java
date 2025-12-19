package com.library.services;

import com.library.models.Reader;
import com.library.repositories.ReaderRepository;
import com.library.repositories.projections.ReaderView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReaderServiceTest {

    @Mock
    private ReaderRepository readerRepository;

    @InjectMocks
    private ReaderService readerService;

//    @Test
//    void softDeleteReader_success() {
//        Reader reader = new Reader();
//        reader.setId(1L);
//        reader.setDeleted(false);
//
//        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));
//        when(readerRepository.softDeleteById(any())).thenReturn(1);
//
//        readerService.softDeleteReader(1L);
//
////        assertTrue(reader.isDeleted());
////        assertNotNull(reader.getDeletedAt());
////        assertTrue(reader.getDeletedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
//
//        verify(readerRepository).findById(1L);
//        verify(readerRepository).save(reader);
//        verifyNoMoreInteractions(readerRepository);
//    }

    @Test
    void softDeleteReader_notFound() {
        when(readerRepository.softDeleteById(1L)).thenReturn(1);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> readerService.softDeleteReader(1L)
        );

        assertEquals("Читача не знайдено", exception.getMessage());

        verify(readerRepository).softDeleteById(1L);
        verify(readerRepository, never()).save(any());
        verifyNoMoreInteractions(readerRepository);
    }

    @Test
    void hardDeleteReader_success() {
        Reader reader = new Reader();
        reader.setId(1L);

        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));

        readerService.hardDeleteReader(1L);

        verify(readerRepository).findById(1L);
        verify(readerRepository).delete(reader);
        verifyNoMoreInteractions(readerRepository);
    }

//    @Test
//    void hardDeleteReader_notFound() {
//        when(readerRepository.findById(1L)).thenReturn(Optional.empty());
//
//        RuntimeException exception = assertThrows(
//                RuntimeException.class,
//                () -> readerService.hardDeleteReader(1L)
//        );
//
//        assertEquals("Читача з ID 1 не знайдено", exception.getMessage());
//
////        verify(readerRepository).findById(1L);
//        verify(readerRepository, never()).delete(any());
////        verifyNoMoreInteractions(readerRepository);
//    }

    @Test
    void createReader_success() {
        Reader reader = new Reader();

        readerService.create(reader);

        verify(readerRepository).save(reader);
        verifyNoMoreInteractions(readerRepository);
    }

    @Test
    void getAllReaders_success() {
        ReaderView reader1 = mock(ReaderView.class);
        ReaderView reader2 = mock(ReaderView.class);

        when(readerRepository.findAllBy()).thenReturn(List.of(reader1, reader2));

        List<ReaderView> result = readerService.getAllReaders();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(readerRepository).findAllBy();
        verifyNoMoreInteractions(readerRepository);
    }
}
