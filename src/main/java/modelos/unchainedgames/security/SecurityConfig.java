package modelos.unchainedgames.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(auth -> auth
                        // -------- RUTAS PÚBLICAS --------
                        .requestMatchers(HttpMethod.POST, "/usuario/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuario/create").anonymous()

                        .requestMatchers(HttpMethod.GET, "/product/**").permitAll()

                        // 👉 TODAS las rutas de solicitudes de empleo son públicas
                        .requestMatchers("/solicitudes-empleo/**").permitAll()

                        // -------- RUTAS RESTRINGIDAS --------
                        .requestMatchers(HttpMethod.POST, "/product/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/product/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/product/**").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/review/me").authenticated()
                        .requestMatchers(HttpMethod.GET, "/review/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/review/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/review/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/review/**").authenticated()

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // CUALQUIER OTRA RUTA: autenticación obligatoria
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

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
