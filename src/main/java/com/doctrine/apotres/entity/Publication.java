package com.doctrine.apotres.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 * ENTITÉ PUBLICATION —
 * ============================================================
 */
@Entity
@Table(name = "publications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le type de publication est obligatoire")
    @Column(name = "type", nullable = false)
    private TypePublication type;

    @NotBlank(message = "Le titre est obligatoire")
    @Column(name = "titre", nullable = false, length = 500)
    private String titre;

    @Lob
    @Column(name = "contenu", columnDefinition = "LONGTEXT")
    private String contenu;

    @Column(name = "categorie", length = 100)
    private String categorie;

    @Column(name = "sous_categorie", length = 200)
    private String sousCategorie;

    @Column(name = "auteur", length = 100)
    private String auteur;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutPublication statut = StatutPublication.BROUILLON;

    // ============================================================
    // ANCIENS CHAMPS AUDIO —
    // ============================================================

    @Column(name = "chemin_audio", length = 500)
    private String cheminAudio;

    @Column(name = "chemin_audio2", length = 500)
    private String cheminAudio2;

    @Column(name = "chemin_audio3", length = 500)
    private String cheminAudio3;

    // ============================================================
    // NOUVEAU —
    // ============================================================
    @ElementCollection(fetch = FetchType.EAGER)
   

    @CollectionTable(
        name = "publication_audios",

        joinColumns = @JoinColumn(name = "publication_id")

    )
    @OrderColumn(name = "position")


    @Column(name = "chemin")
   
    private List<String> cheminsAudio = new ArrayList<>();
   

    // ============================================================
    // AUTRES CHAMPS (inchangés)
    // ============================================================

    @Column(name = "resume", length = 1000)
    private String resume;

    @Column(name = "predicateur", length = 200)
    private String predicateur;

    @Column(name = "image_une", length = 500)
    private String imageUne;

    @Column(name = "chemin_pdf", length = 500)
    private String cheminPdf;

    @Column(name = "lien_video", length = 500)
    private String lienVideo;

    @Column(name = "jour_zoom", length = 20)
    private String jourZoom;

    @Column(name = "date_session", length = 50)
    private String dateSession;

    @Column(name = "tags", length = 500)
    private String tags;

    @Column(name = "commentaires_actifs")
    private Boolean commentairesActifs = true;

    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @Column(name = "date_publication")
    private LocalDateTime datePublication;

    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Commentaire> commentaires;

    @PrePersist
    protected void onCreate() {
        this.dateCreation     = LocalDateTime.now();
        this.dateModification = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateModification = LocalDateTime.now();
    }

    public enum TypePublication {
        ENSEIGNEMENT,
        AUDIO,
        ZOOM,
        RADIO,
        LIVRE,
        VIDEO
    }

    public enum StatutPublication {
        PUBLIE,
        BROUILLON,
        SUSPENDU
    }
}
