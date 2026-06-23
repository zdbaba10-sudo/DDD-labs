package com.example.dddlabs;

import java.util.Optional;

public interface AvisRepository
{
    Optional<Avis> findById(AvisId id);

    void save(Avis avis);
}
