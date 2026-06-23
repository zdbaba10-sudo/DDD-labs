package com.example.dddlabs;

import lombok.Data;

@Data
public class Avis
{

    private AvisId id;
    private RenterId renterId;
    private StatusAvis status;
    private AvisMessage message;
    private AvisNote note;
    private AvisCaptchaTag captchaTag;

    Avis(AvisId id, RenterId renterId, AvisMessage avisMessage, AvisNote note)
    {
        this.id = id;
        this.renterId = renterId;
        this.status = StatusAvis.EN_ATTENTE;
        this.message = avisMessage;
        this.note = note;
        this.captchaTag = new AvisCaptchaTag(false);
    }

    public static Avis creer(AvisId id, RenterId auteurId, AvisMessage avisMessage, AvisNote note)
    {
        return new Avis(id, auteurId, avisMessage, note);
    }

    public void publier(AvisMessage message, AvisNote note)
    {
        this.setMessage(message);
        this.note = note;
    }

    public void valider()
    {
        if (assertStatusEnAttenteOuAModerer())
        {
            throw new BusinessException("Seul un avis en attente ou à modérer peut être validé");
        }
        status = StatusAvis.PUBLIE;
    }

    private boolean assertStatusEnAttenteOuAModerer()
    {
        return status != StatusAvis.EN_ATTENTE && status != StatusAvis.A_MODERER;
    }

    public void invalider()
    {
        if (assertStatusEnAttenteOuAModerer())
        {
            throw new BusinessException("Seul un avis en attente ou à modérer peut être invalidé");
        }
        status = StatusAvis.INVALIDE;
    }

    public void captchaValider()
    {
        // Implémentation du captcha
        if (captchaTag.value())
        {
            throw new BusinessException("Le captcha a déjà été tagué");
        }
        captchaTag = new AvisCaptchaTag(true);
    }

}
