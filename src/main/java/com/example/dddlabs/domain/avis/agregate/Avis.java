package com.example.dddlabs.domain.avis.agregate;

import com.example.dddlabs.domain.avis.valueobjects.AvisCaptchaTag;
import com.example.dddlabs.domain.avis.valueobjects.AvisId;
import com.example.dddlabs.domain.avis.valueobjects.AvisMessage;
import com.example.dddlabs.domain.avis.valueobjects.AvisNote;
import com.example.dddlabs.domain.avis.valueobjects.RenterId;
import com.example.dddlabs.domain.avis.valueobjects.StatusAvis;
import com.example.dddlabs.exception.BusinessException;
import lombok.Data;

/**
 * Agrégat racine Avis
 * Responsabilités: gérer le cycle de vie d'un avis (création, validation, invalidation)
 */
@Data
public class Avis {

    private AvisId id;
    private RenterId renterId;
    private StatusAvis status;
    private AvisMessage message;
    private AvisNote note;
    private AvisCaptchaTag captchaTag;

    Avis(AvisId id, RenterId renterId, AvisMessage avisMessage, AvisNote note) {
        this.id = id;
        this.renterId = renterId;
        this.status = StatusAvis.EN_ATTENTE;
        this.message = avisMessage;
        this.note = note;
        this.captchaTag = new AvisCaptchaTag(false);
    }

    /**
     * Factory method pour créer un nouvel avis
     */
    public static Avis creer(AvisId id, RenterId auteurId, AvisMessage avisMessage, AvisNote note) {
        return new Avis(id, auteurId, avisMessage, note);
    }

    public void publier(AvisMessage message, AvisNote note) {
        this.setMessage(message);
        this.note = note;
    }

    /**
     * Valide un avis en attente ou à modérer
     * @throws BusinessException si le statut ne permet pas la validation
     */
    public void valider() {
        if (!isValidStatusForValidation()) {
            throw new BusinessException("Seul un avis en attente ou à modérer peut être validé");
        }
        status = StatusAvis.PUBLIE;
    }

    /**
     * Invalide un avis en attente ou à modérer
     * @throws BusinessException si le statut ne permet pas l'invalidation
     */
    public void invalider() {
        if (!isValidStatusForInvalidation()) {
            throw new BusinessException("Seul un avis en attente ou à modérer peut être invalidé");
        }
        status = StatusAvis.INVALIDE;
    }

    /**
     * Vérifie si le statut actuel permet une validation
     */
    private boolean isValidStatusForValidation() {
        return status == StatusAvis.EN_ATTENTE || status == StatusAvis.A_MODERER;
    }

    /**
     * Vérifie si le statut actuel permet une invalidation
     */
    private boolean isValidStatusForInvalidation() {
        return status == StatusAvis.EN_ATTENTE || status == StatusAvis.A_MODERER;
    }

    /**
     * Valide le captcha de l'avis
     * @throws BusinessException si le captcha a déjà été validé
     */
    public void captchaValider() {
        if (captchaTag.value()) {
            throw new BusinessException("Le captcha a déjà été validé");
        }
        captchaTag = new AvisCaptchaTag(true);
    }

}

