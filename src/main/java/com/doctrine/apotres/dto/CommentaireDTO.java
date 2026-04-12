package com.doctrine.apotres.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.doctrine.apotres.entity.Commentaire.StatutCommentaire;
import java.time.LocalDateTime;

/**
 * ============================================================
 * DTO COMMENTAIRE
 * ============================================================
 */
public class CommentaireDTO {

    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        // Nom du visiteur 
        @NotBlank(message = "Le nom est obligatoire")
        private String nom;

        
        @Email(message = "Format d'email invalide")
        private String email;

        // Texte du commentaire
        @NotBlank(message = "Le message est obligatoire")
        private String message;

        // ID de la publication commentée 
        private Long publicationId;

        private String source;
    }

    /**
     * Données envoyées au dashboard pour la modération
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String nom;
        private String email;
        private String message;
        private String source;
        private StatutCommentaire statut;
        private LocalDateTime dateCreation;
        private String articleTitre; // Titre de la publication associée
        private Long publicationId;
    }
}
