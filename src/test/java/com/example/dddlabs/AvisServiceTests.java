package com.example.dddlabs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AvisServiceTests
{
    AvisRepository avisRepository = new AvisRepositoryFake();
    AvisService avisService = new AvisService(avisRepository);

    @Test
    void publier()
    {
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
    void valider()
    {
        //Given
        AvisId avisId = new AvisId("1");
        RenterId renterId = new RenterId("1");
        AvisMessage avisMessage = new AvisMessage("toto");
        AvisNote avisNote = new AvisNote(5);
        Avis avis = Avis.creer(avisId, renterId, avisMessage, avisNote);
        AvisRepositoryFake.avisMap.clear();
        AvisRepositoryFake.avisMap.put(avisId, avis);
        // When
        Avis result = avisService.valider(avisId);
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(StatusAvis.PUBLIE, result.getStatus());
    }

    @Test
    void invalider()
    {
        //Given
        AvisId avisId = new AvisId("1");
        RenterId renterId = new RenterId("1");
        AvisMessage avisMessage = new AvisMessage("toto");
        AvisNote avisNote = new AvisNote(5);
        Avis avis = Avis.creer(avisId, renterId, avisMessage, avisNote);
        AvisRepositoryFake.avisMap.clear();
        AvisRepositoryFake.avisMap.put(avisId, avis);
        // When
        Avis result = avisService.invalider(avisId);
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(StatusAvis.INVALIDE, result.getStatus());
    }
}