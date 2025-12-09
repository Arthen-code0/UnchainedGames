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

        // 👉 RUTAS QUE NO PASAN POR JWT (públicas)
        if (requestPath.startsWith("/usuario/login")
                || requestPath.startsWith("/solicitudes-empleo")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Si no hay Authorization o no empieza por Bearer, seguimos sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        TokenDataDTO datos = jwtService.extractTokenData(token);

        if (datos != null
                && SecurityContextHolder.getContext().getAuthentication() == null
                && !jwtService.isExpired(token)) {

            Usuario usuario = (Usuario) usuarioService.loadUserByUsername(datos.getMailUsername());

            if (usuario != null) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                usuario,
                                null,
                                usuario.getAuthorities()
                        );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
