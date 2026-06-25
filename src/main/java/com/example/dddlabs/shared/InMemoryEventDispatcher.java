package com.example.dddlabs.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryEventDispatcher implements EventDispatcher, EventSubscriber {
    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryEventDispatcher.class);

    private final Map<Class<? extends Event>, List<EventHandler<? extends Event>>> handlers = new HashMap<>();

    public <E extends Event> void subscribe(Class<E> eventType, EventHandler<E> consumer) {
        LOGGER.trace("Subscribing to {} by event handler {}", eventType, consumer);
        handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(consumer);
    }

    public <E extends Event> void dispatch(E event) {
        LOGGER.info("Dispatching the event {}", event);
        if (handlers.containsKey(event.getClass())) {
            for (EventHandler<? extends Event> handler : handlers.get(event.getClass())) {
                ((EventHandler<E>) handler).handle(event);
            }
        } else {
            LOGGER.info("No event handlers for the event {}", event.getClass());
        }
    }
}
