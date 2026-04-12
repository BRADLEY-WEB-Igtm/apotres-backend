package com.doctrine.apotres.config;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

 
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(404).body(
            Map.of("erreur", "Ressource introuvable", "message", e.getMessage())
        );
    }

   
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentials(BadCredentialsException e) {
        return ResponseEntity.status(401).body(
            Map.of("erreur", "Identifiants incorrects",
                   "message", "Nom d\'utilisateur ou mot de passe invalide")
        );
    }

    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDenied(AccessDeniedException e) {
        return ResponseEntity.status(403).body(
            Map.of("erreur", "Accès refusé",
                   "message", "Vous n\'avez pas les permissions pour cette action")
        );
    }

   
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException e) {
        Map<String, String> erreurs = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(err ->
            erreurs.put(err.getField(), err.getDefaultMessage())
        );
        return ResponseEntity.status(400).body(
            Map.of("erreur", "Données invalides", "champs", erreurs)
        );
    }

   
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String, String>> handleFileTooLarge(MaxUploadSizeExceededException e) {
        return ResponseEntity.status(413).body(
            Map.of("erreur", "Fichier trop grand",
                   "message", "La taille maximum autorisée est 50MB")
        );
    }

  
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.status(400).body(
            Map.of("erreur", "Requête invalide", "message", e.getMessage())
        );
    }

   
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception e) {
        
        System.err.println("Erreur non gérée : " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(500).body(
            Map.of("erreur", "Erreur interne du serveur",
                   "message", "Une erreur inattendue s\'est produite. Réessayez plus tard.")
        );
    }
}
