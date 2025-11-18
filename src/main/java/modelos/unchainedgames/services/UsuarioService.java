package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.UsuarioCreateDTO;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.repository.IUsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsuarioService implements IUsuarioServices, UserDetailsService {

    private IUsuarioRepository repository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findTopByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    @Override
    public List<Usuario> obtenerTodosUsuarios() {
        return repository.findAll();
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorId(Integer id) {
        return repository.findById(id);
    }

    @Override
    public void createUsuario(UsuarioCreateDTO dto) {
        // Verificar si el usuario ya existe
        if (repository.findTopByUsername(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        Usuario newUsuario = new Usuario();
        newUsuario.setName(dto.getName());
        newUsuario.setSurnames(dto.getSurnames());
        newUsuario.setPhoneNumber(dto.getPhoneNumber());
        newUsuario.setEmail(dto.getEmail());
        newUsuario.setUsername(dto.getEmail()); // Asumiendo que el username es el email
        newUsuario.setPassword(passwordEncoder.encode(dto.getPassword())); // Encriptar contraseña
        newUsuario.setAddresses(dto.getAddresses());
        newUsuario.setEnabled(true); // Usuario habilitado por defecto
        newUsuario.setVerificationCode(generateVerificationCode()); // Código de verificación

        repository.save(newUsuario);

        // Enviar código de verificación por email
        emailService.sendVerificationEmail(newUsuario.getEmail(), newUsuario.getVerificationCode());
    }

    @Override
    public void updateUsuario(Integer id, UsuarioCreateDTO dto) {
        repository.findById(id).ifPresent(usuario -> {
            usuario.setName(dto.getName());
            usuario.setSurnames(dto.getSurnames());
            usuario.setPhoneNumber(dto.getPhoneNumber());
            usuario.setEmail(dto.getEmail());
            // No actualizar la contraseña aquí, usar método específico
            usuario.setAddresses(dto.getAddresses());
            repository.save(usuario);
        });
    }

    @Override
    public void deleteUsuario(Integer id) {
        repository.deleteById(id);
    }

    public Usuario loginUsuario(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return repository.findTopByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        } catch (Exception e) {
            throw new RuntimeException("Credenciales inválidas", e);
        }
    }

    public Usuario registroUsuario(UsuarioCreateDTO dto) {
        // Verificar si el usuario ya existe
        if (repository.findTopByUsername(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El usuario ya está registrado");
        }

        Usuario newUsuario = new Usuario();
        newUsuario.setName(dto.getName());
        newUsuario.setSurnames(dto.getSurnames());
        newUsuario.setPhoneNumber(dto.getPhoneNumber());
        newUsuario.setEmail(dto.getEmail());
        newUsuario.setUsername(dto.getEmail());
        newUsuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUsuario.setAddresses(dto.getAddresses());
        newUsuario.setEnabled(false); // No habilitado hasta verificación
        newUsuario.setVerificationCode(generateVerificationCode());

        Usuario usuarioGuardado = repository.save(newUsuario);

        // Enviar código de verificación
        emailService.sendVerificationEmail(usuarioGuardado.getEmail(), usuarioGuardado.getVerificationCode());

        return usuarioGuardado;
    }

    // Cambiar contraseña
    public void cambiarContrasena(String username, String currentPassword, String newPassword) {
        Usuario usuario = repository.findTopByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Verificar contraseña actual
        if (!passwordEncoder.matches(currentPassword, usuario.getPassword())) {
            throw new RuntimeException("La contraseña actual es incorrecta");
        }

        // Actualizar contraseña
        usuario.setPassword(passwordEncoder.encode(newPassword));
        repository.save(usuario);
    }

    // Cambiar contraseña con código de recuperación
    public void cambiarContrasenaConCodigo(String email, String codigoRecuperacion, String newPassword) {
        Usuario usuario = repository.findByEmailAndRecoveryCode(email, codigoRecuperacion)
                .orElseThrow(() -> new RuntimeException("Código de recuperación inválido o expirado"));

        usuario.setPassword(passwordEncoder.encode(newPassword));
        usuario.setRecoveryCode(null); // Limpiar código de recuperación
        repository.save(usuario);
    }

    // Enviar código de recuperación
    public void enviarCodigoRecuperacion(String email) {
        Usuario usuario = repository.findTopByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String recoveryCode = generateRecoveryCode();
        usuario.setRecoveryCode(recoveryCode);
        repository.save(usuario);

        // Enviar código por email
        emailService.sendRecoveryCodeEmail(email, recoveryCode);
    }

    // Verificación de cuenta
    public boolean verificarCuenta(String email, String codigoVerificacion) {
        Optional<Usuario> usuarioOpt = repository.findByEmailAndVerificationCode(email, codigoVerificacion);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setEnabled(true);
            usuario.setVerificationCode(null);
            repository.save(usuario);
            return true;
        }

        return false;
    }

    // Métodos auxiliares
    private String generateVerificationCode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    private String generateRecoveryCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Obtener usuario actualmente autenticado
    public Optional<Usuario> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return repository.findTopByUsername(username);
        }
        return Optional.empty();
    }
}