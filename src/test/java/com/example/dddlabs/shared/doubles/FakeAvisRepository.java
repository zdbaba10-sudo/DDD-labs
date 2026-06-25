package com.example.dddlabs.shared.doubles;

import com.example.dddlabs.domain.avis.agregate.Avis;
import com.example.dddlabs.domain.avis.ports.AvisRepository;
import com.example.dddlabs.domain.avis.valueobjects.AvisId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeAvisRepository implements AvisRepository {
    private final Map<AvisId, Avis> avisMap = new HashMap<>();

    @Override
    public void save(Avis avis) {
        avisMap.put(avis.getId(), avis);
    }

    @Override
    public void delete(Avis avis) {
        avisMap.remove(avis.getId());
    }

    @Override
    public Optional<Avis> findById(AvisId id) {
        return Optional.ofNullable(avisMap.get(id));
    }
}

