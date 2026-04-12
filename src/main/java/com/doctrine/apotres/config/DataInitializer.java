package com.doctrine.apotres.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.doctrine.apotres.repository.UtilisateurRepository;
import com.doctrine.apotres.service.AuthService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
   

    @Autowired
    private AuthService authService;
  
    @Override
    public void run(String... args) throws Exception {

        
        if (utilisateurRepository.count() == 0) {
           

            System.out.println("🔧 Aucun admin trouvé — création du compte Super Admin par défaut...");

            
            authService.creerAdmin(
                "fridolinbradley",          
                "admin@doctrineapotres.com", 
                "Admin@2026!",              
                "Super Admin"               
            );

            System.out.println("✅ Compte Super Admin créé !");
            System.out.println("   Username : fridolinbradley");
            System.out.println("   Mot de passe : Admin@2026!");
            System.out.println("   ⚠️  CHANGEZ LE MOT DE PASSE APRÈS LA PREMIÈRE CONNEXION !");

        } else {
           
            System.out.println("✅ " + utilisateurRepository.count() + " admin(s) trouvé(s) en base.");
        }
    }
}
