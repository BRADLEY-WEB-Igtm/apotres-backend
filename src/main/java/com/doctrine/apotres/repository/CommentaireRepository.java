package com.doctrine.apotres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.doctrine.apotres.entity.Commentaire;
import com.doctrine.apotres.entity.Commentaire.StatutCommentaire;

/**
 * ============================================================
 * REPOSITORY COMMENTAIRE
 * ============================================================
 */
@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

    /**
     * Trouve les commentaires par statut avec pagination
     */
    Page<Commentaire> findByStatutOrderByDateCreationDesc(
        StatutCommentaire statut,
        Pageable pageable
    );

    /**
     * Trouve les commentaires d'une publication spécifique
     */
    Page<Commentaire> findByPublicationIdAndStatutOrderByDateCreationDesc(
        Long publicationId,
        StatutCommentaire statut,
        Pageable pageable
    );

    /**
     * Compte les commentaires en attente
     */
    long countByStatut(StatutCommentaire statut);

    // Compte les commentaires approuvés d'une publication spécifique
    long countByPublicationIdAndStatut(Long publicationId, StatutCommentaire statut);

    /**
     * Trouve les commentaires par source
     */
    Page<Commentaire> findBySourceAndStatutOrderByDateCreationDesc(
        String source,
        StatutCommentaire statut,
        Pageable pageable
    );
}
