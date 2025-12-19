package com.library.services;

import com.library.models.Reader;
import com.library.repositories.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.library.repositories.projections.ReaderView;
import java.util.List;

@Service
public class ReaderService {

    @Autowired
    private ReaderRepository readerRepository;

    public void create(Reader reader) {
        readerRepository.save(reader);
    }

    public void softDeleteReader(Long id) {
        Reader reader = readerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Читача не знайдено"));

        reader.setDeleted(true);
        reader.setDeletedAt(java.time.LocalDateTime.now());
        readerRepository.save(reader);
    }

    public void hardDeleteReader(Long id) {
        Reader reader = readerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Читача не знайдено"));

        readerRepository.delete(reader);
    }

    public List<ReaderView> getAllReaders() {
        return readerRepository.findAllBy();
    }

    public List<ReaderView> getActiveReaders() {
        return readerRepository.findAllByDeletedFalse();
    }

    public List<ReaderView> getTop() {
      return readerRepository.findTop3ReadersByBorrowCount();
    }
}
