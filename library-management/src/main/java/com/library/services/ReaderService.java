package com.library.services;

import com.library.models.Reader;
import com.library.repositories.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ReaderService {
    @Autowired private ReaderRepository readerRepository;

    public void softDeleteReader(Long id) {
        Reader reader = readerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Читача не знайдено"));

        reader.setDeleted(true);
        reader.setDeletedAt(LocalDateTime.now());
        readerRepository.save(reader);
    }
}