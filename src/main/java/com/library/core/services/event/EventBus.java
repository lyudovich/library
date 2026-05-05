package com.library.core.services.event;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Component
public class EventBus {

    private final List<Consumer<Object>> listeners = new ArrayList<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public void publish(Object event) {
        for (Consumer<Object> listener : listeners) {
            executor.submit(() -> listener.accept(event));
        }
    }

    public void subscribe(Consumer<Object> listener) {
        listeners.add(listener);
    }
}
