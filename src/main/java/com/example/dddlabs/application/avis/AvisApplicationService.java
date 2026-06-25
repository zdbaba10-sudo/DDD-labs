package com.example.dddlabs.application.avis;

import com.example.dddlabs.exception.BusinessException;
import com.example.dddlabs.domain.avis.events.AvisSupprimeEvent;
import com.example.dddlabs.domain.avis.agregate.Avis;
import com.example.dddlabs.domain.avis.ports.AvisRepository;
import com.example.dddlabs.domain.avis.valueobjects.AvisId;
import com.example.dddlabs.domain.avis.valueobjects.AvisMessage;
import com.example.dddlabs.domain.avis.valueobjects.AvisNote;
import com.example.dddlabs.domain.avis.valueobjects.RenterId;
import com.example.dddlabs.shared.EventDispatcher;

public class AvisApplicationService {
    private final AvisRepository avisRepository;
    private final EventDispatcher eventDispatcher;

    public AvisApplicationService(AvisRepository aAvisRepository, EventDispatcher eventDispatcher) {
        avisRepository = aAvisRepository;
        this.eventDispatcher = eventDispatcher;
    }

    public Avis publier(String message, int note, String uuidRenter) {
        AvisId avisId = new AvisId("1");
        RenterId renterId = new RenterId(uuidRenter);
        AvisMessage avisMessage = new AvisMessage(message);
        AvisNote avisNote = new AvisNote(note);
        Avis avis = Avis.creer(avisId, renterId, avisMessage, avisNote);
        avisRepository.save(avis);
        return avis;
    }

    public Avis valider(String avisIdString) {
        AvisId avisId = new AvisId(avisIdString);
        Avis avis = avisRepository.findById(avisId).orElseThrow(() -> new BusinessException("Avis not found"));
        avis.valider();
        avisRepository.save(avis);
        return avis;
    }

    public Avis invalider(AvisId avisId) {
        Avis avis = avisRepository.findById(avisId).orElseThrow(() -> new BusinessException("Avis not found"));
        avis.invalider();
        avisRepository.save(avis);
        return avis;
    }

    public void supprimer(String avisIdString) {
        AvisId avisId = new AvisId(avisIdString);
        Avis avis = avisRepository.findById(avisId).orElseThrow(() -> new BusinessException("Avis not found"));
        avisRepository.delete(avis);
        eventDispatcher.dispatch(new AvisSupprimeEvent(avisId));

    }
}

