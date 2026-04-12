package com.doctrine.apotres.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.doctrine.apotres.dto.AuthDTO;
import com.doctrine.apotres.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> connecter(
        @Valid @RequestBody AuthDTO.LoginRequest request
    ) {
        try {
            
            AuthDTO.LoginResponse response = authService.connecter(request);

          
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {

            return ResponseEntity
                .status(401)
                .body(Map.of(
                    "erreur",  "Identifiants incorrects",
                    "message", "Nom d'utilisateur ou mot de passe invalide"
                ));

        } catch (Exception e) {

            System.err.println("Erreur login : " + e.getMessage());
            return ResponseEntity
                .status(500)
                .body(Map.of(
                    "erreur",  "Erreur interne du serveur",
                    "message", e.getMessage()
                ));
        }
    }

    @GetMapping("/verifier")
    public ResponseEntity<?> verifierToken() {

        String username = SecurityContextHolder
            .getContext()           
            .getAuthentication()    
            .getName();             

        return ResponseEntity.ok(Map.of(
            "valide",   true,
            "username", username
        ));
    }
}
