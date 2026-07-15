package com.duoc.sistemainscripcion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                // Solo rol DESCARGA puede descargar comprobantes
                .requestMatchers("/inscripciones/*/descargar").hasAuthority("ROLE_DESCARGA")
                // Rol ADMIN gestiona inscripciones
                .requestMatchers("/inscripciones/crear").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/inscripciones/*/subir").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/inscripciones/*/modificar").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/inscripciones/*/eliminar").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/inscripciones/*/eliminar-archivo").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/inscripciones/consultar").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/inscripciones/listar").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/inscripciones/listar-archivos").hasAuthority("ROLE_ADMIN")
                // BFF accesible por ambos roles
                .requestMatchers("/bff/**").authenticated()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
            );
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthoritiesClaimName("extension_rol");
        converter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtConverter;
    }
}
