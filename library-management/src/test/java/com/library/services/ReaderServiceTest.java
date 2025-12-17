package com.library.services;

import com.library.models.Reader;
import com.library.repositories.ReaderRepository;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@ActiveProfiles("test")
//@Transactional

class ReaderServiceTest {
    @Autowired private ReaderService readerService;
    @Autowired private ReaderRepository readerRepository;
    @Test
    void softDeleteReader() {
        Long readerId = 1L;

        readerService.softDeleteReader(readerId);

        Reader deletedReader = readerRepository.findById(readerId).orElse(null);
        assertNotNull(deletedReader);
        assertTrue(deletedReader.isDeleted(), "Прапорець isDeleted має бути true");
        assertNotNull(deletedReader.getDeletedAt(), "Дата видалення має бути заповнена");

        List<Reader> activeReaders = readerRepository.findAllActiveReaders();
        boolean existsInActive = activeReaders.stream().anyMatch(r -> r.getId().equals(readerId));
        assertFalse(existsInActive, "Видалений читач не має з'являтися у списку активних");
    }

    @Test
    void hardDeleteReader() {
        Reader reader = new Reader();
        reader.setDeletedAt(null);
        reader.setFullName("test1");
        reader.setEmail("test1");
        readerService.create(reader);
        readerService.hardDeleteReader(reader.getId());
        assertFalse(readerRepository.findById(reader.getId()).isPresent());
    }
}