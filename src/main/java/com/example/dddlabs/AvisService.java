package com.example.dddlabs;

public class AvisService
{
    private final AvisRepository avisRepository;

    public AvisService(AvisRepository aAvisRepository)
    {
        avisRepository = aAvisRepository;
    }

    public Avis publier(String message, int note, String uuidRenter)
    {
        AvisId avisId = new AvisId("1");
        RenterId renterId = new RenterId(uuidRenter);
        AvisMessage avisMessage = new AvisMessage(message);
        AvisNote avisNote = new AvisNote(note);
        Avis avis = Avis.creer(avisId, renterId, avisMessage, avisNote);
        avisRepository.save(avis);
        return avis;
    }

    public Avis valider(AvisId avisId)
    {
        Avis avis = avisRepository.findById(avisId).orElseThrow(() -> new BusinessException("Avis not found"));
        avis.valider();
        avisRepository.save(avis);
        return avis;
    }

    public Avis invalider(AvisId avisId)
    {
        Avis avis = avisRepository.findById(avisId).orElseThrow(() -> new BusinessException("Avis not found"));
        avis.invalider();
        avisRepository.save(avis);
        return avis;
    }
}
