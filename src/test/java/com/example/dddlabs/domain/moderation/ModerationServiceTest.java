package com.example.dddlabs.domain.moderation;

import com.example.dddlabs.application.avis.AvisApplicationService;
import com.example.dddlabs.domain.avis.agregate.Avis;
import com.example.dddlabs.domain.avis.valueobjects.AvisId;
import com.example.dddlabs.domain.avis.valueobjects.AvisMessage;
import com.example.dddlabs.domain.avis.valueobjects.AvisNote;
import com.example.dddlabs.domain.avis.valueobjects.RenterId;
import com.example.dddlabs.infrastructure.moderation.AvisSupprimeEventHandler;
import com.example.dddlabs.shared.InMemoryEventDispatcher;
import com.example.dddlabs.shared.doubles.FakeAvisRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModerationServiceTest {

    @Test
    void handleAvisSupprime() {
        InMemoryEventDispatcher eventBus = new InMemoryEventDispatcher();

        FakeAvisRepository avisRepository = new FakeAvisRepository();

        Avis avis = Avis.creer(new AvisId("1"), new RenterId("renter-1"), new AvisMessage("Très bon service"), new AvisNote(5));

        avisRepository.save(avis);

        SpyModerationService moderationService = new SpyModerationService();

        new AvisSupprimeEventHandler(moderationService, eventBus);

        AvisApplicationService avisService = new AvisApplicationService(avisRepository, eventBus);

        avisService.supprimer("1");

        assertTrue(avisRepository.findById(new AvisId("1")).isEmpty());
        assertEquals(new AvisId("1"), moderationService.avisIdRecu);
    }

    static class SpyModerationService extends ModerationService {

        AvisId avisIdRecu;

        @Override
        public void handleAvisSupprime(AvisId avisId) {
            this.avisIdRecu = avisId;
        }
    }
}

