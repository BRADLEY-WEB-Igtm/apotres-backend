package com.doctrine.apotres.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
@org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
    
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;


    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${app.cors.allowed-origins:https://doctrinedesapotres.com,https://www.doctrinedesapotres.com,http://localhost:5500,http://localhost:5501}")
    private String allowedOrigins;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
      
            .csrf(csrf -> csrf.disable())

          
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))


            .authorizeHttpRequests(auth -> auth

              
                .requestMatchers("/api/auth/login").permitAll()


                .requestMatchers(HttpMethod.POST, "/api/commentaires/**").permitAll()

                .requestMatchers(HttpMethod.GET, "/api/publications/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/commentaires/approuves/**").permitAll()

                .requestMatchers(HttpMethod.POST, "/api/prieres").permitAll()
              
                .requestMatchers("/uploads/**").permitAll()

                .requestMatchers("/api/admin/**").hasAnyRole("SUPER_ADMIN", "ADMIN", "EDITEUR")

                .anyRequest().authenticated()
            )

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        List<String> origines = java.util.Arrays.stream(allowedOrigins.split(","))
            .map(String::trim)
            .collect(java.util.stream.Collectors.toList());
        config.setAllowedOrigins(origines);

        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        config.setAllowedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type",
            "Accept"            
        ));

       
        config.setAllowCredentials(true);

        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }


    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }
}
