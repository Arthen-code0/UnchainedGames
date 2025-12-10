package modelos.unchainedgames.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        // ⬇️ PERMITIR TODAS LAS OPERACIONES SOBRE PRODUCTOS
                        .requestMatchers(HttpMethod.GET, "/product/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/product/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/product/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/product/**").permitAll()

                        // ⬇️ OPCIONAL: abrir también usuario create / login
                        .requestMatchers(HttpMethod.POST, "/usuario/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuario/create").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuario/verify").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuario/resend-verification").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuario/request-recovery").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuario/reset-password").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuario/").permitAll()

                        // el resto, de momento, también abiertos
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
