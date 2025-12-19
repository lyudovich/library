package com.library.services;

import com.library.models.Reader;
import com.library.repositories.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.library.repositories.projections.ReaderView;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReaderService {

    @Autowired
    private ReaderRepository readerRepository;

    public void create(Reader reader) {
        readerRepository.save(reader);
    }

    @Transactional
    public void softDeleteReader(Long id) {
        int updated = readerRepository.softDeleteById(id);

        if (updated == 0) {
            throw new RuntimeException("Читача не знайдено");
        }
    }


    @Transactional
    public void hardDeleteReader(Long id) {
        int deleted = readerRepository.hardDeleteById(id);

        if (deleted == 0) {
            throw new RuntimeException("Читача з ID " + id + " не знайдено");
        }
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

    public List<ReaderView> getAllActiveReaders() {
        return readerRepository.findAllActiveReaders();
    }
}
