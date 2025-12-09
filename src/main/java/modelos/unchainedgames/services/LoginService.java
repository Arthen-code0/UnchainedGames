package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.LoginCreateDTO;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.repository.IUsuarioRepository;
import modelos.unchainedgames.security.JWTService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class LoginService {

    private final IUsuarioRepository usuarioRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    public Map<String, Object> loguearUsuario(LoginCreateDTO dto) {
        // 1. Buscar usuario por email
        Usuario usuario = usuarioRepository.findTopByEmailEquals(dto.getEmail());

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // 2. Verificar contraseña
        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        // 3. Verificar si el usuario está habilitado
        if (!usuario.getEnabled()) {
            throw new RuntimeException("Usuario deshabilitado");
        }

        // 4. Generar token JWT
        String token = jwtService.generateToken(usuario);

        // 5. Crear respuesta
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("token", token);

        // 6. Agregar información del usuario
        Map<String, Object> usuarioInfo = new HashMap<>();
        usuarioInfo.put("id", usuario.getId());
        usuarioInfo.put("email", usuario.getEmail());
        usuarioInfo.put("nombre", usuario.getName());
        usuarioInfo.put("apellidos", usuario.getSurnames());
        usuarioInfo.put("rol", usuario.getRol().name());
        usuarioInfo.put("telefono", usuario.getPhoneNumber());

        respuesta.put("usuario", usuarioInfo);

        return respuesta;
    }
}