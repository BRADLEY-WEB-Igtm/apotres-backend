package com.doctrine.apotres.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "commentaires")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commentaire {

    // Identifiant unique auto-généré par MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ============================================================
    // NOM DU VISITEUR
    // ============================================================
    @NotBlank(message = "Le nom est obligatoire")
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    // ============================================================
    // EMAIL DU VISITEUR
    // ============================================================
    @Email(message = "Format d'email invalide")
    @Column(name = "email", length = 150)
    private String email;

    // ============================================================
    // TEXTE DU COMMENTAIRE
    // ============================================================
    @NotBlank(message = "Le message est obligatoire")
    @Lob
    @Column(name = "message", columnDefinition = "TEXT", nullable = false)
    private String message;

    // ============================================================
    // RELATION AVEC LA PUBLICATION
    // ============================================================
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id")
    private Publication publication;

    // ============================================================
    // SOURCE DU COMMENTAIRE
    // ============================================================
    @Column(name = "source", length = 100)
    private String source;

    // ============================================================
    // STATUT DU COMMENTAIRE
    // ============================================================
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutCommentaire statut = StatutCommentaire.EN_ATTENTE;
    // Par défaut = EN_ATTENTE (tout commentaire doit être modéré)

    // ============================================================
    // ADRESSE IP DU VISITEUR
    // ============================================================
    @Column(name = "adresse_ip", length = 50)
    private String adresseIp;

    // Date de soumission du commentaire
    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    // Date de modération 
    @Column(name = "date_moderation")
    private LocalDateTime dateModeration;

    @Column(name = "modere_par", length = 100)
    private String moderePar;

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
    }

    // ============================================================
    // ÉNUMÉRATION — statuts possibles d'un commentaire
    // ============================================================
    public enum StatutCommentaire {
        EN_ATTENTE,
        APPROUVE,   
        REJETE      
    }
}
