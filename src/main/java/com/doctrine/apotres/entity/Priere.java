package com.doctrine.apotres.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * ============================================================
 * ENTITÉ PRIERE
 * ============================================================
 */
@Entity
@Table(name = "prieres")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Priere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "sujet", length = 200)
    private String sujet;

    @NotBlank(message = "Le message est obligatoire")
    @Lob
    @Column(name = "message", columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(name = "anonyme")
    private Boolean anonyme = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutPriere statut = StatutPriere.EN_ATTENTE;

    // Date de soumission
    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
    }

    public enum StatutPriere {
        EN_ATTENTE, 
        PRISE_EN_CHARGE,
        ARCHIVEE   
    }
}
