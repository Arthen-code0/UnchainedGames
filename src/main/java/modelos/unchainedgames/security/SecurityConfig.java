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
                        // ---------- RUTAS PÚBLICAS ----------
                        .requestMatchers(HttpMethod.POST, "/usuario/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuario/create").anonymous()

                        .requestMatchers(HttpMethod.GET, "/product/**").permitAll()

                        .requestMatchers("/solicitudes-empleo/**").permitAll()

                        // ---------- PRODUCTOS (solo admin puede CREAR/EDITAR/BORRAR) ----------
                        .requestMatchers(HttpMethod.POST, "/product/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/product/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/product/**").hasAuthority("ADMIN")

                        // ---------- REVIEWS ----------
                        // Ver reviews de cualquier producto
                        .requestMatchers(HttpMethod.GET, "/review/**").permitAll()
                        // Ver mis reviews
                        .requestMatchers(HttpMethod.GET, "/review/me").authenticated()
                        // Crear / editar / borrar -> usuario logueado
                        .requestMatchers(HttpMethod.POST, "/review/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/review/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/review/**").authenticated()

                        // ---------- PEDIDOS ----------
                        // Crear pedido (desde carrito): usuario logueado (USUARIO o ADMIN)
                        .requestMatchers(HttpMethod.POST, "/pedido/create")
                        .hasAnyAuthority("USUARIO", "ADMIN")

                        // Ver mis pedidos
                        .requestMatchers(HttpMethod.GET, "/pedido/me")
                        .hasAnyAuthority("USUARIO", "ADMIN")

                        // Ver detalle / listado general de pedidos:
                        // solo ADMIN (el método de servicio además revisará que
                        // si no eres admin, el pedido sea tuyo)
                        .requestMatchers(HttpMethod.GET, "/pedido/**")
                        .hasAuthority("ADMIN")

                        // No exponemos DELETE de pedidos: nadie borra
                        // (si algún día añades endpoint DELETE /pedido/**, protégelo con ADMIN)

                        // Preflight CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ---------- CUALQUIER OTRA COSA: autenticar ----------
                        .anyRequest().authenticated()
                );

        // Meter el filtro JWT antes del de username/password
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
