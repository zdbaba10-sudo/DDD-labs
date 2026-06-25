package com.example.dddlabs.shared;

public interface EventSubscriber {
    <E extends Event> void subscribe(Class<E> eventType, EventHandler<E> consumer);
}
