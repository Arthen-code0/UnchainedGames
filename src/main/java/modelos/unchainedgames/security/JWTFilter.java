package modelos.unchainedgames.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.services.UsuarioService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String requestPath = request.getServletPath();

        // ⛔️ SOLO IGNORAMOS EL LOGIN, NO EL CREATE
        if (requestPath.contains("/usuario/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Si no hay Authorization o no empieza por Bearer, seguimos sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Quitamos "Bearer " y nos quedamos con el token
        String token = authHeader.substring(7);
        TokenDataDTO datos = jwtService.extractTokenData(token);

        // Si el token es válido, no está expirado y todavía no hay autenticación en el contexto…
        if (datos != null
                && SecurityContextHolder.getContext().getAuthentication() == null
                && !jwtService.isExpired(token)) {

            // Cargamos el usuario desde la BD usando el mail/username del token
            Usuario usuario = (Usuario) usuarioService.loadUserByUsername(datos.getMailUsername());

            if (usuario != null) {
                // Creamos el objeto de autenticación con sus authorities (roles)
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                usuario,                      // principal (usuario)
                                null,                         // no necesitamos la password aquí
                                usuario.getAuthorities()      // roles: ADMIN / USUARIO
                        );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Guardamos la autenticación en el contexto de Spring Security
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continuamos la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
