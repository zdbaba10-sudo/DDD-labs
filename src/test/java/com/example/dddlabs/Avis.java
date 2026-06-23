package com.example.dddlabs;

import lombok.Data;

@Data
public class Avis {

    private AvisId id;
    private ClientId auteurId;
    private StatusAvis status;
    private String message;
    private int note;
    private boolean captchaTag;

    Avis(AvisId id, ClientId auteurId) {
        this.id = id;
        this.auteurId = auteurId;
        this.status = StatusAvis.EN_ATTENTE;
    }

    public static Avis creer(AvisId id, ClientId auteurId) {
        return new Avis(id, auteurId);
    }

    public void publier(String message, int note) {
        this.setMessage(message);
        this.note = note;
    }


    public void valider() {
        if (assertStatusEnAttenteOuAModerer()) {
            throw new BusinessException("Seul un avis en attente ou à modérer peut être validé");
        }
        status = StatusAvis.PUBLIE;
    }

    private boolean assertStatusEnAttenteOuAModerer() {
        return status != StatusAvis.EN_ATTENTE && status != StatusAvis.A_MODERER;
    }

    public void invalider() {
        if (assertStatusEnAttenteOuAModerer()) {
            throw new BusinessException("Seul un avis en attente ou à modérer peut être invalidé");
        }
        status = StatusAvis.INVALIDE;
    }

    public void captchaValider() {
        // Implémentation du captcha
        if (captchaTag) {
            throw new BusinessException("Le captcha a déjà été tagué");
        }
        captchaTag = true;
    }

}
