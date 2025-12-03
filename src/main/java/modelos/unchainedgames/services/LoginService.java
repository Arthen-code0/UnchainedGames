package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.LoginCreateDTO;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.repository.IUsuarioRepository;
import modelos.unchainedgames.security.JWTService;
import modelos.unchainedgames.security.MecanismoSeguridad;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class LoginService {

    private IUsuarioRepository repository;
    private MecanismoSeguridad seguridad;
    private JWTService jwtService;


    public String loguearUsuario(LoginCreateDTO dto){
        Usuario usuario = repository.findTopByEmailEquals(dto.getEmail());

        if (usuario != null && seguridad.GetPasswordEncoder().matches(dto.getPassword(), usuario.getPassword())){
            return jwtService.generateToken(usuario);
        }

        return "Fallo de Autenticacion";
    }
}
