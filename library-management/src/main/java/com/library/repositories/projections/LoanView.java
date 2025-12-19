package com.library.repositories.projections;

import java.time.LocalDate;

public interface LoanView {

    Long getId();

    LocalDate getLoanDate();
    LocalDate getDueDate();
    LocalDate getReturnDate();

    BookInfo getBook();
    ReaderInfo getReader();

    interface BookInfo {
        Long getId();
        String getTitle();
        String getIsbn();
    }

    interface ReaderInfo {
        Long getId();
        String getFullName();
        String getEmail();
    }
}
