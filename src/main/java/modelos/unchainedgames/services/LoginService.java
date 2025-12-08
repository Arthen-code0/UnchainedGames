package modelos.unchainedgames.services;

import lombok.RequiredArgsConstructor;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.security.JWTService;
import modelos.unchainedgames.dto.LoginCreateDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public String loguearUsuario(LoginCreateDTO dto) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),   // username (email)
                        dto.getPassword() // contraseña en texto plano
                )
        );

        Usuario usuario = (Usuario) auth.getPrincipal();

        return jwtService.generateToken(usuario);
    }
}
