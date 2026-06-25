package com.example.dddlabs.application.avis;

import com.example.dddlabs.shared.doubles.FakeAvisRepository;
import com.example.dddlabs.domain.avis.agregate.Avis;
import com.example.dddlabs.domain.avis.ports.AvisRepository;
import com.example.dddlabs.domain.avis.valueobjects.AvisId;
import com.example.dddlabs.domain.avis.valueobjects.AvisMessage;
import com.example.dddlabs.domain.avis.valueobjects.AvisNote;
import com.example.dddlabs.domain.avis.valueobjects.RenterId;
import com.example.dddlabs.domain.avis.valueobjects.StatusAvis;
import com.example.dddlabs.shared.InMemoryEventDispatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AvisApplicationServiceTest {
    private AvisRepository avisRepository;
    private AvisApplicationService avisService;

    @BeforeEach
    void setUp() {
        avisRepository = new FakeAvisRepository();
        avisService = new AvisApplicationService(avisRepository, new InMemoryEventDispatcher());
    }

    @Test
    void publier() {
        //Given
        // When
        Avis result = avisService.publier("Toto", 5, "1");
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Toto", result.getMessage().value());
        Assertions.assertEquals(5, result.getNote().value());
        Assertions.assertEquals("1", result.getRenterId().value());

    }

    @Test
    void valider() {
        //Given
        AvisId avisId = new AvisId("1");
        RenterId renterId = new RenterId("1");
        AvisMessage avisMessage = new AvisMessage("toto");
        AvisNote avisNote = new AvisNote(5);
        Avis avis = Avis.creer(avisId, renterId, avisMessage, avisNote);
        avisRepository.save(avis);
        // When
        Avis result = avisService.valider(avisId.id());
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(StatusAvis.PUBLIE, result.getStatus());
    }

    @Test
    void invalider() {
        //Given
        AvisId avisId = new AvisId("1");
        RenterId renterId = new RenterId("1");
        AvisMessage avisMessage = new AvisMessage("toto");
        AvisNote avisNote = new AvisNote(5);
        Avis avis = Avis.creer(avisId, renterId, avisMessage, avisNote);
        avisRepository.save(avis);
        // When
        Avis result = avisService.invalider(avisId);
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(StatusAvis.INVALIDE, result.getStatus());
    }
}
