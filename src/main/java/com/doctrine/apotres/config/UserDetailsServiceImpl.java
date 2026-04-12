package com.doctrine.apotres.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.doctrine.apotres.entity.Utilisateur;
import com.doctrine.apotres.repository.UtilisateurRepository;

import java.util.Collections;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {

        Utilisateur utilisateur = utilisateurRepository
            .findByUsername(username)

            .orElseThrow(() ->
                new UsernameNotFoundException(
                    "Utilisateur non trouvé : " + username
                )
            );

        if (!utilisateur.getActif()) {
            throw new UsernameNotFoundException(
                "Compte désactivé : " + username
            );
        }

        return new User(
            utilisateur.getUsername(),

            utilisateur.getMotDePasse(),


            Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().name())
            
            )
        );
    }
}
