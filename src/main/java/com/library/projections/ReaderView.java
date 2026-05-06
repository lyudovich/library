package com.library.projections;

import java.time.LocalDateTime;

public interface ReaderView {
    Long getId();
    String getFullName();
    String getEmail();
    Boolean getDeleted();
    LocalDateTime getDeletedAt();
    LocalDateTime getUpdatedAt();
}
