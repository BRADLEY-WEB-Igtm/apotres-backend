package com.doctrine.apotres.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * ============================================================
 * ENTITÉ UTILISATEUR
 * ============================================================
 */
@Entity
@Table(name = "utilisateurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {

    // Identifiant unique auto-généré
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ============================================================
    // NOM D'UTILISATEUR
    // ============================================================
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    // ============================================================
    // EMAIL
    // ============================================================
    @Email(message = "Format d'email invalide")
    @NotBlank(message = "L'email est obligatoire")
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    // ============================================================
    // NOM COMPLET
    // ============================================================
    @Column(name = "nom_complet", length = 200)
    private String nomComplet;

    // ============================================================
    // RÔLE
    // ============================================================
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleUtilisateur role = RoleUtilisateur.ADMIN;

    // ============================================================
    // ACTIF
    // ============================================================
    @Column(name = "actif")
    private Boolean actif = true;

    @Lob
    @Column(name = "photo_profil", columnDefinition = "LONGTEXT")

    private String photoProfil;

    // Date de création du compte
    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    // Date de dernière connexion
    @Column(name = "derniere_connexion")
    private LocalDateTime derniereConnexion;

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
    }

    // ============================================================
    // ÉNUMÉRATION — rôles disponibles
    // ============================================================
    public enum RoleUtilisateur {
        SUPER_ADMIN, 
        ADMIN,       
        EDITEUR      
    }
}
