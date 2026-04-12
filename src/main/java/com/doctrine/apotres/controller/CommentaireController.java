package com.doctrine.apotres.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctrine.apotres.dto.CommentaireDTO;
import com.doctrine.apotres.entity.Commentaire.StatutCommentaire;
import com.doctrine.apotres.service.CommentaireService;

import java.util.Map;

@RestController
@CrossOrigin
public class CommentaireController {

    @Autowired
    private CommentaireService commentaireService;


    @PostMapping("/api/commentaires")
    public ResponseEntity<?> soumettre(
        @Valid @RequestBody CommentaireDTO.Request request,
        HttpServletRequest httpRequest

    ) {
        try {
            CommentaireDTO.Response response =
                commentaireService.soumettre(request, "general", httpRequest);

            return ResponseEntity.status(201).body(Map.of(
                "message", "Commentaire soumis avec succès. Il sera visible après modération.",
                "id", response.getId()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(Map.of("erreur", e.getMessage()));
        }
    }

  
    @PostMapping("/api/commentaires/{source}")
    public ResponseEntity<?> soumettreAvecSource(
        @PathVariable String source,
        @Valid @RequestBody CommentaireDTO.Request request,
        HttpServletRequest httpRequest
    ) {
        try {
            CommentaireDTO.Response response =
                commentaireService.soumettre(request, source, httpRequest);

            return ResponseEntity.status(201).body(Map.of(
                "message", "Commentaire soumis avec succès. Il sera visible après modération.",
                "id", response.getId()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(Map.of("erreur", e.getMessage()));
        }
    }

    @GetMapping("/api/commentaires/approuves/{publicationId}")
    public ResponseEntity<Page<CommentaireDTO.Response>> listerApprouves(
        @PathVariable Long publicationId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
            commentaireService.listerApprouvesPourPublication(
                publicationId, page, size
            )
        );
    }

 
    @GetMapping("/api/admin/commentaires")
    public ResponseEntity<Page<CommentaireDTO.Response>> listerPourAdmin(
        @RequestParam(required = false) StatutCommentaire statut,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(
            commentaireService.listerPourAdmin(statut, page, size)
        );
    }


    @PutMapping("/api/admin/commentaires/{id}/approuver")
    public ResponseEntity<?> approuver(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(commentaireService.approuver(id));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(Map.of("erreur", e.getMessage()));
        }
    }


    @PutMapping("/api/admin/commentaires/{id}/rejeter")
    public ResponseEntity<?> rejeter(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(commentaireService.rejeter(id));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(Map.of("erreur", e.getMessage()));
        }
    }

    @DeleteMapping("/api/admin/commentaires/{id}")
    public ResponseEntity<?> supprimer(@PathVariable Long id) {
        try {
            commentaireService.supprimer(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(Map.of("erreur", e.getMessage()));
        }
    }
}
