package com.example.dddlabs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
        assertTrue(avis.isCaptchaTag());
    }

    @Test
    void captcha_deja_valide() {
        Avis avis = avisPublie();
        avis.captchaValider();
        assertThrows(BusinessException.class, avis::captchaValider);
    }

    private static Avis avisPublie() {
        AvisId id = new AvisId();
        ClientId auteurId = new ClientId();
        Avis avis = Avis.creer(id, auteurId);
        ;
        avis.publier("toto", 5);
        return avis;
    }


}
