// AuthService.java
package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.AuthResponse;
import modelos.unchainedgames.dto.LoginRequest;
import modelos.unchainedgames.dto.UsuarioCreateDTO;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.repository.IUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private IUsuarioRepository usuarioRepository;

    // Token simple sin JWT
    private String generateToken(Usuario usuario) {
        return "token-" + usuario.getId() + "-" + System.currentTimeMillis();
    }

    public AuthResponse login(LoginRequest loginRequest) {
        // Buscar usuario por email
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginRequest.getEmail());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // Verificar contraseña (sin encriptación por ahora)
            if (!usuario.getPassword().equals(loginRequest.getPassword())) {
                throw new RuntimeException("Contraseña incorrecta");
            }

            // Verificar si el usuario está habilitado
            if (usuario.getEnabled() != null && !usuario.getEnabled()) {
                throw new RuntimeException("Usuario deshabilitado");
            }

            String token = generateToken(usuario);
            return new AuthResponse(token, usuario, "Login exitoso");
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    public AuthResponse register(UsuarioCreateDTO dto) {
        // Verificar si el email ya existe
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Crear nuevo usuario
        Usuario newUsuario = new Usuario();
        newUsuario.setName(dto.getName());
        newUsuario.setSurnames(dto.getSurnames());
        newUsuario.setEmail(dto.getEmail());
        newUsuario.setUsername(dto.getEmail());
        newUsuario.setPassword(dto.getPassword()); // Sin encriptación por ahora
        newUsuario.setPhoneNumber(dto.getPhoneNumber());
        newUsuario.setAddresses(dto.getAddresses());
        newUsuario.setEnabled(true); // Usuario activo directamente
        newUsuario.setRol(modelos.unchainedgames.listed.Rol.USUARIO);

        Usuario usuarioGuardado = usuarioRepository.save(newUsuario);

        String token = generateToken(usuarioGuardado);
        return new AuthResponse(token, usuarioGuardado, "Registro exitoso");
    }
}