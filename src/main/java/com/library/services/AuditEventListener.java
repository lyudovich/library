package com.library.services;

import com.library.models.BookCreatedEvent;
import com.library.services.event.EventBus;
import org.springframework.stereotype.Component;

@Component
public class AuditEventListener {

    public AuditEventListener(EventBus bus, AuditService auditService) {
        bus.subscribe(event -> {
            if (event instanceof BookCreatedEvent e) {
                auditService.log("BOOK_CREATED_ASYNC", e.title);
            }
        });
    }
}
