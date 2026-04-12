package com.doctrine.apotres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.doctrine.apotres.config.JwtUtil;
import com.doctrine.apotres.dto.AuthDTO;
import com.doctrine.apotres.entity.Utilisateur;
import com.doctrine.apotres.repository.UtilisateurRepository;

import java.time.LocalDateTime;

/**
 * ============================================================
 * SERVICE AUTHENTIFICATION
 * ============================================================
 */
@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    // Gère le processus d'authentification Spring Security

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    // Pour charger et mettre à jour l'utilisateur en BD

    @Autowired
    private JwtUtil jwtUtil;
    // Pour générer le token JWT après connexion réussie

    @Autowired
    private PasswordEncoder passwordEncoder;
   

    /**
     * Connecte un administrateur
     *
     */
    public AuthDTO.LoginResponse connecter(AuthDTO.LoginRequest request) {

        // ---- Étape 1 : Authentifier via Spring Security ----
       
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getMotDePasse()
               
            )
        );
        // Si les identifiants sont incorrects → 

      
        Utilisateur utilisateur = utilisateurRepository
            .findByUsername(request.getUsername())
            .orElseThrow(() ->
                new BadCredentialsException("Utilisateur non trouvé")
            );

  -
        utilisateur.setDerniereConnexion(LocalDateTime.now());
        utilisateurRepository.save(utilisateur);
      
        String token = jwtUtil.genererToken(utilisateur.getUsername());

        return new AuthDTO.LoginResponse(
            token,
            "Bearer",
            utilisateur.getId(),
            utilisateur.getUsername(),
            utilisateur.getNomComplet(),
            utilisateur.getRole(),
            86400000L // 24h en millisecondes
        );
    }

    /**
     * Crée le premier compte Super Admin
     */
    public Utilisateur creerAdmin(
        String username,
        String email,
        String motDePasse,
        String nomComplet
    ) {
        Utilisateur admin = new Utilisateur();
        admin.setUsername(username);
        admin.setEmail(email);

        
        admin.setMotDePasse(passwordEncoder.encode(motDePasse));

        admin.setNomComplet(nomComplet);
        admin.setRole(Utilisateur.RoleUtilisateur.SUPER_ADMIN);
        admin.setActif(true);

        return utilisateurRepository.save(admin);
    }
}
