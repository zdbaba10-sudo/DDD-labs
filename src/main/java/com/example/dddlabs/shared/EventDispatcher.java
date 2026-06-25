package com.example.dddlabs.shared;

public interface EventDispatcher {
    <E extends Event> void dispatch(E event);
}
