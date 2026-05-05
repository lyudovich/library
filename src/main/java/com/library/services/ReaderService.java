package com.library.services;

import com.library.models.Reader;
import com.library.repositories.ReaderRepository;
import com.library.userModel.UserReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReaderService implements IReaderService {

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

    public List<UserReader> getAllReaders() {
        return readerRepository.findAllBy().stream()
                .map(this::toUserReader)
                .toList();
    }

    public List<UserReader> getActiveReaders() {
        return readerRepository.findAllByDeletedFalse().stream()
                .map(this::toUserReader)
                .toList();
    }

    public List<UserReader> getTop() {
        return readerRepository.findTop3ReadersByBorrowCount().stream()
                .map(this::toUserReader)
                .toList();
    }

    public List<UserReader> getAllActiveReaders() {
        return readerRepository.findAllActiveReaders().stream()
                .map(this::toUserReader)
                .toList();
    }

    private UserReader toUserReader(com.library.projections.ReaderView rv) {
        UserReader ur = new UserReader();
        ur.setId(rv.getId());
        ur.setFullName(rv.getFullName());
        ur.setEmail(rv.getEmail());
        ur.setDeleted(rv.getDeleted());
        ur.setDeletedAt(rv.getDeletedAt());
        ur.setUpdatedAt(rv.getUpdatedAt());
        return ur;
    }
}