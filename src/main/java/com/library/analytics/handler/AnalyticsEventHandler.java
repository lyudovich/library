package com.library.analytics.handler;

import com.library.analytics.acl.BookEventMapper;

import com.library.core.models.BookStats;
import com.library.core.services.event.EventBus;
import com.library.models.BookCreatedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AnalyticsEventHandler {

    private final Map<Long, BookStats> statsStore = new HashMap<>();

    public AnalyticsEventHandler(EventBus bus) {

        bus.subscribe(event -> {
            if (event instanceof BookCreatedEvent e) {

                BookStats stats = BookEventMapper.map(e);

                statsStore.merge(
                        stats.getBookId(),
                        stats,
                        (oldVal, newVal) -> {
                            oldVal.setCreatedCount(oldVal.getCreatedCount() + 1);
                            return oldVal;
                        }
                );

                System.out.println("ANALYTICS UPDATED: " + stats.getTitle());
            }
        });
    }
}
