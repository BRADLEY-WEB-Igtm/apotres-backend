package com.doctrine.apotres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.doctrine.apotres.entity.Utilisateur;
import java.util.Optional;

/**
 * ============================================================
 * REPOSITORY UTILISATEUR
 * Gère les comptes administrateurs
 * ============================================================
 */
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByUsername(String username);

    /**
     * Trouve un utilisateur par son email
   
     */
    Optional<Utilisateur> findByEmail(String email);

    /**
     * Vérifie si un username est déjà utilisé
 
     */
    boolean existsByUsername(String username);

    /**
     * Vérifie si un email est déjà utilisé
     */
    boolean existsByEmail(String email);
}
