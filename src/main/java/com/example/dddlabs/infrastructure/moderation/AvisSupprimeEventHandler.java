package com.example.dddlabs.infrastructure.moderation;

import com.example.dddlabs.domain.avis.events.AvisSupprimeEvent;
import com.example.dddlabs.domain.moderation.ModerationService;
import com.example.dddlabs.shared.EventHandler;
import com.example.dddlabs.shared.EventSubscriber;

public class AvisSupprimeEventHandler implements EventHandler<AvisSupprimeEvent> {

    private final ModerationService moderationService;

    public AvisSupprimeEventHandler(ModerationService moderationService, EventSubscriber eventSubscriber) {
        this.moderationService = moderationService;
        eventSubscriber.subscribe(AvisSupprimeEvent.class, this);
    }


    @Override
    public void handle(AvisSupprimeEvent event) {
        moderationService.handleAvisSupprime(event.avisId());
    }
}

