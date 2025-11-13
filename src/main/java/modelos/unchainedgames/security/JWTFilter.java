package modelos.unchainedgames.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import modelos.unchainedgames.models.Usuario;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // CORREGIDO: Mejorar la lógica de exclusión de rutas
        if (request.getServletPath().contains("/auth") ||
                request.getServletPath().contains("/public") ||
                request.getMethod().equals("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.substring(7);
            TokenDataDTO tokenDataDTO = jwtService.extractTokenData(token);

            if(tokenDataDTO != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null &&
                    !jwtService.isExpired(token)){

                Usuario usuario = (Usuario) userDetailsService.loadUserByUsername(tokenDataDTO.getUsername());

                if(usuario != null){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            usuario,
                            null, // La contraseña no es necesaria después de la autenticación
                            usuario.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Log the error but don't break the filter chain
            logger.error("Error procesando JWT token", e);
        }

        filterChain.doFilter(request, response);
    }
}