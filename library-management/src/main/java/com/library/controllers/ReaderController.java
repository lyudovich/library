package com.library.controllers;

import com.library.models.Reader;
import com.library.repositories.projections.ReaderView;
import com.library.services.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/readers")
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @GetMapping
    public List<ReaderView> getAllReaders() {
        return readerService.getAllReaders();
    }

    @GetMapping("/top")
    public List<ReaderView> getTopReaders() {
        return readerService.getTop();
    }

    @GetMapping("/active")
    public List<ReaderView> getActiveReaders() {
        return readerService.getAllActiveReaders();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createReader(@RequestBody Reader reader) {
        readerService.create(reader);
    }

    @DeleteMapping("/{id}")
    public void softDeleteReader(@PathVariable Long id) {
        readerService.softDeleteReader(id);
    }

    @DeleteMapping("/{id}/hard")
    public void hardDeleteReader(@PathVariable Long id) {
        readerService.hardDeleteReader(id);
    }
}
