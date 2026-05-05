package com.library.models;

public class BookCreatedEvent {
    public final Long bookId;
    public final String title;

    public BookCreatedEvent(Long bookId, String title) {
        this.bookId = bookId;
        this.title = title;
    }
}
