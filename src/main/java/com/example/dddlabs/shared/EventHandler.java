package com.example.dddlabs.shared;

public interface EventHandler<T extends Event> {
    void handle(T event);
}
