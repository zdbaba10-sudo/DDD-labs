package com.example.dddlabs.domain.avis.events;

import com.example.dddlabs.domain.avis.valueobjects.AvisId;
import com.example.dddlabs.shared.Event;

public record AvisSupprimeEvent(AvisId avisId) implements Event {
}

