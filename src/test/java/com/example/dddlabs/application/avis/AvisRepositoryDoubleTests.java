package com.example.dddlabs.application.avis;

import com.example.dddlabs.shared.doubles.FakeAvisRepository;
import com.example.dddlabs.domain.avis.agregate.Avis;
import com.example.dddlabs.domain.avis.ports.AvisRepository;
import com.example.dddlabs.domain.avis.valueobjects.AvisId;
import com.example.dddlabs.domain.avis.valueobjects.AvisMessage;
import com.example.dddlabs.domain.avis.valueobjects.AvisNote;
import com.example.dddlabs.domain.avis.valueobjects.RenterId;
import com.example.dddlabs.domain.avis.valueobjects.StatusAvis;
import com.example.dddlabs.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AvisRepositoryDoubleTests {

    @Test
    void stub_repository_retourne_un_avis_et_permet_sa_validation() {
        AvisId avisId = new AvisId("1");
        Avis avis = Avis.creer(avisId, new RenterId("r-1"), new AvisMessage("ok"), new AvisNote(4));
        StubAvisRepository repository = new StubAvisRepository(avis);
        ServiceAvecDoubles service = new ServiceAvecDoubles(repository);

        service.validerAvis(avisId);

        assertEquals(StatusAvis.PUBLIE, avis.getStatus());
        assertEquals(avis, repository.savedAvis);
    }

    @Test
    void fake_repository_persiste_un_avis_en_memoire() {
        FakeAvisRepository repository = new FakeAvisRepository();
        ServiceAvecDoubles service = new ServiceAvecDoubles(repository);
        AvisId avisId = new AvisId("2");
        RenterId renterId = new RenterId("r-2");

        service.publierAvis(avisId, renterId, "super produit", 5);

        Avis avisSauvegarde = repository.findById(avisId).orElseThrow();
        assertEquals("super produit", avisSauvegarde.getMessage().value());
        assertEquals(5, avisSauvegarde.getNote().value());
        assertEquals(StatusAvis.EN_ATTENTE, avisSauvegarde.getStatus());
    }

    @Test
    void mock_repository_verifie_les_interactions_du_service() {
        AvisId avisId = new AvisId("3");
        Avis avis = Avis.creer(avisId, new RenterId("r-3"), new AvisMessage("x"), new AvisNote(1));
        AvisRepository repository = mock(AvisRepository.class);
        when(repository.findById(avisId)).thenReturn(Optional.of(avis));
        ServiceAvecDoubles service = new ServiceAvecDoubles(repository);

        service.invaliderAvis(avisId);

        verify(repository).findById(avisId);
        verify(repository).save(avis);
        assertEquals(StatusAvis.INVALIDE, avis.getStatus());
    }

    @Test
    void lever_une_exception_quand_l_avis_est_introuvable() {
        AvisRepository repository = mock(AvisRepository.class);
        AvisId avisId = new AvisId("404");
        when(repository.findById(avisId)).thenReturn(Optional.empty());
        ServiceAvecDoubles service = new ServiceAvecDoubles(repository);

        assertThrows(BusinessException.class, () -> service.validerAvis(avisId));
    }

    static class ServiceAvecDoubles {
        private final AvisRepository repository;

        ServiceAvecDoubles(AvisRepository repository) {
            this.repository = repository;
        }

        void publierAvis(AvisId avisId, RenterId renterId, String message, int note) {
            Avis avis = Avis.creer(avisId, renterId, new AvisMessage(message), new AvisNote(note));
            avis.publier(new AvisMessage(message), new AvisNote(note));
            repository.save(avis);
        }

        void validerAvis(AvisId avisId) {
            Avis avis = repository.findById(avisId)
                    .orElseThrow(() -> new BusinessException("Avis introuvable"));
            avis.valider();
            repository.save(avis);
        }

        void invaliderAvis(AvisId avisId) {
            Avis avis = repository.findById(avisId)
                    .orElseThrow(() -> new BusinessException("Avis introuvable"));
            avis.invalider();
            repository.save(avis);
        }
    }

    static class StubAvisRepository implements AvisRepository {
        private final Avis avis;
        private Avis savedAvis;

        StubAvisRepository(Avis avis) {
            this.avis = avis;
        }

        @Override
        public Optional<Avis> findById(AvisId id) {
            return Optional.of(avis);
        }

        @Override
        public void delete(Avis avis) {
            // Not used in these tests.
        }

        @Override
        public void save(Avis avis) {
            this.savedAvis = avis;
        }
    }
}

