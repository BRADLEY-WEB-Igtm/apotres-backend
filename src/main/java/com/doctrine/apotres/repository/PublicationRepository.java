package com.doctrine.apotres.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.doctrine.apotres.entity.Publication;
import com.doctrine.apotres.entity.Publication.TypePublication;
import com.doctrine.apotres.entity.Publication.StatutPublication;

import java.util.List;

/**
 * ============================================================
 * REPOSITORY PUBLICATION
 * ============================================================
 */
@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    /**
     * Trouve toutes les publications d'un certain statut
     */
    Page<Publication> findByStatutOrderByDateCreationDesc(
        StatutPublication statut,
        Pageable pageable
    );

    /**
     * Trouve toutes les publications publiées d'un certain type
     */
    Page<Publication> findByTypeAndStatutOrderByDateCreationDesc(
        TypePublication type,
        StatutPublication statut,
        Pageable pageable
    );

    /**
     * Trouve les publications d'une catégorie donnée
     */
    Page<Publication> findByCategorieAndStatutOrderByDateCreationDesc(
        String categorie,
        StatutPublication statut,
        Pageable pageable
    );

    /**
     * Compte les publications par statut
     */
    long countByStatut(StatutPublication statut);

    /**
     * Compte les publications par type
     */
    long countByType(TypePublication type);

    /**
     * Recherche par titre (LIKE = cherche le terme n'importe où dans le titre)
     */
    @Query("SELECT p FROM Publication p WHERE LOWER(p.titre) LIKE LOWER(CONCAT('%', :terme, '%')) AND p.statut = :statut ORDER BY p.dateCreation DESC")
    Page<Publication> rechercherParTitre(
        @Param("terme") String terme,
        @Param("statut") StatutPublication statut,
        Pageable pageable
    );

    /**
     * Trouve les publications Zoom par jour (LUNDI ou JEUDI)
     */
    Page<Publication> findByTypeAndJourZoomAndStatutOrderByDateCreationDesc(
        TypePublication type,
        String jourZoom,
        StatutPublication statut,
        Pageable pageable
    );

    /**
     * Trouve les 6 dernières publications pour le tableau du dashboard
     */
    List<Publication> findTop6ByOrderByDateCreationDesc();
}
