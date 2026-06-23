package com.example.dddlabs;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AvisRepositoryFake implements AvisRepository
{
    static final Map<AvisId, Avis> avisMap = new HashMap<>();

    @Override
    public void save(Avis avis)
    {
        avisMap.put(avis.getId(), avis);
    }

    @Override
    public Optional<Avis> findById(AvisId id)
    {
        return Optional.ofNullable(AvisRepositoryFake.avisMap.get(id));
    }
}
