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
                // Solo rol DESCARGA puede descargar
                .requestMatchers("/guias/*/descargar").hasAuthority("ROLE_DESCARGA")
                // Rol ADMIN puede usar el resto
                .requestMatchers("/guias/crear").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/guias/*/subir").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/guias/*/modificar").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/guias/*/eliminar").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/guias/*/eliminar-archivo").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/guias/consultar").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/guias/listar").hasAuthority("ROLE_ADMIN")
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