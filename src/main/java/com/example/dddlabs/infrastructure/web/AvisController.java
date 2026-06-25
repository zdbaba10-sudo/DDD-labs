package com.example.dddlabs.infrastructure.web;

import com.example.dddlabs.application.avis.AvisApplicationService;
import com.example.dddlabs.domain.avis.agregate.Avis;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AvisController {
    protected final AvisApplicationService avisService;

    public AvisController(AvisApplicationService avisService) {
        this.avisService = avisService;
    }

    @PostMapping("/avis")
    public ResponseEntity<String> createAvis(String message, int note) {
        // Call the service to create an avis
        String uuidRenter = UUID.randomUUID().toString(); // TODO mettre dans un value object
        Avis avisId = avisService.publier(message, note, uuidRenter);
        return ResponseEntity.ok(avisId.toString());
    }

    @PatchMapping("/avis/{avisId}/valider")
    public ResponseEntity<String> validerAvis(String avisId) {
        Avis avis = avisService.valider(avisId);
        return ResponseEntity.ok(avisId);
    }
}

