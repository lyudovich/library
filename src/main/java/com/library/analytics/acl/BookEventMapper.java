package com.library.analytics.acl;

import com.library.core.models.BookStats;
import com.library.core.models.BookCreatedEvent;

public class BookEventMapper {

    public static BookStats map(BookCreatedEvent event) {
        BookStats stats = new BookStats();
        stats.setBookId(event.bookId);
        stats.setTitle(event.title);
        stats.setCreatedCount(1);
        return stats;
    }
}
