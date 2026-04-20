package com.library.services;

import com.library.models.Reader;
import com.library.projections.ReaderView;
import java.util.List;

public interface IReaderService {
    void create(Reader reader);
    void softDeleteReader(Long id);
    void hardDeleteReader(Long id);
    List<ReaderView> getAllReaders();
    List<ReaderView> getActiveReaders();
    List<ReaderView> getTop();
    List<ReaderView> getAllActiveReaders();
}