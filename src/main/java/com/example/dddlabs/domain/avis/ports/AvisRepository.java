package com.example.dddlabs.domain.avis.ports;

import com.example.dddlabs.domain.avis.agregate.Avis;
import com.example.dddlabs.domain.avis.valueobjects.AvisId;

import java.util.Optional;

public interface AvisRepository {
    void delete(Avis avis);

    Optional<Avis> findById(AvisId id);

    void save(Avis avis);
}

