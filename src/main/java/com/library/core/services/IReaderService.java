package com.library.core.services;

import com.library.core.models.Reader;
import com.library.userModel.UserReader;
import java.util.List;

public interface IReaderService {
    void create(Reader reader);
    void softDeleteReader(Long id);
    void hardDeleteReader(Long id);
    List<UserReader> getAllReaders();
    List<UserReader> getActiveReaders();
    List<UserReader> getTop();
    List<UserReader> getAllActiveReaders();
}
