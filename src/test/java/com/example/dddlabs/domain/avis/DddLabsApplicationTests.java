package com.example.dddlabs.domain.avis;

import com.example.dddlabs.domain.avis.agregate.Avis;
import com.example.dddlabs.domain.avis.valueobjects.AvisId;
import com.example.dddlabs.domain.avis.valueobjects.AvisMessage;
import com.example.dddlabs.domain.avis.valueobjects.AvisNote;
import com.example.dddlabs.domain.avis.valueobjects.RenterId;
import com.example.dddlabs.domain.avis.valueobjects.StatusAvis;
import com.example.dddlabs.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DddLabsApplicationTests {

    @Test
    void publier_un_avis() {
        Avis avis = avisPublie();
        assertEquals(StatusAvis.EN_ATTENTE, avis.getStatus());

    }

    @Test
    void un_avis_valide_ne_peut_pas_etre_revalide() {
        Avis avis = avisPublie();
        avis.valider();
        assertThrows(BusinessException.class, avis::valider);

    }

    @Test
    void un_avis_invalide_ne_peut_pas_etre_valide() {
        Avis avis = avisPublie();
        avis.invalider();
        assertThrows(BusinessException.class, avis::valider);

    }

    @Test
    void validation_captcha() {
        Avis avis = avisPublie();
        avis.captchaValider();
        assertTrue(avis.getCaptchaTag().value());
    }

    @Test
    void captcha_deja_valide() {
        Avis avis = avisPublie();
        avis.captchaValider();
        assertThrows(BusinessException.class, avis::captchaValider);
    }

    private static Avis avisPublie() {
        AvisId id = new AvisId("1");
        RenterId auteurId = new RenterId("1");
        AvisMessage avisMessage = new AvisMessage("toto");
        AvisNote avisNote = new AvisNote(5);
        return Avis.creer(id, auteurId, avisMessage, avisNote);
    }
}
