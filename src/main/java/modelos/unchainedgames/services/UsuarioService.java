package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.LoginCreateDTO;
import modelos.unchainedgames.dto.UsuarioCreateDTO;
import modelos.unchainedgames.dto.UsuarioMostrarDTO;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.repository.IUsuarioRepository;
import modelos.unchainedgames.security.MecanismoSeguridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    @Autowired
    private IUsuarioRepository repository;

    @Autowired
    private MecanismoSeguridad seguridad;

    @Autowired
    private EmailService emailService;

    // --- CRUD ---
    public List<UsuarioMostrarDTO> obtenerTodosUsuarios() {
    return repository.findAll().stream()
            .map(UsuarioMostrarDTO::new)
            .collect(Collectors.toList());
}


    public Usuario obtenerUsuariosPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void createUsuario(UsuarioCreateDTO dto) {
        // Verificar si el email ya existe
        if (repository.findTopByEmailEquals(dto.getEmail()) != null) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario newUsuario = new Usuario();
        newUsuario.setName(dto.getName());
        newUsuario.setSurnames(dto.getSurnames());
        newUsuario.setPhoneNumber(dto.getPhoneNumber());
        newUsuario.setEmail(dto.getEmail());
        newUsuario.setUsername(dto.getEmail());  // ← AÑADE esta línea
        newUsuario.setPassword(seguridad.GetPasswordEncoder().encode(dto.getPassword()));
        if (dto.getAddresses() != null) {
            newUsuario.setAddresses(dto.getAddresses());
        }

        // Usuario no habilitado hasta verificar
        newUsuario.setEnabled(false);

        // Generar código de verificación
        String verificationCode = generateVerificationCode();
        newUsuario.setVerificationCode(verificationCode);
        newUsuario.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(24));

        repository.save(newUsuario);

        // Enviar email de verificación
        try {
            emailService.sendVerificationEmail(newUsuario.getEmail(), verificationCode);
        } catch (Exception e) {
            // Log del error pero no fallar el registro
            System.err.println("Error enviando email: " + e.getMessage());
        }
    }

    public void updateUsuario(Integer id, UsuarioCreateDTO dto) {
        Usuario usuario = obtenerUsuariosPorId(id);

        usuario.setName(dto.getName());
        usuario.setSurnames(dto.getSurnames());
        usuario.setPhoneNumber(dto.getPhoneNumber());
        usuario.setAddresses(dto.getAddresses());

        repository.save(usuario);
    }

    public void deleteUsuario(Integer id) {
        repository.deleteById(id);
    }

    // --- AUTH + SEGURIDAD ---

   public boolean verifyAccount(String email, String code) {
        Usuario usuario = repository.findTopByEmailEquals(email);

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (usuario.getEnabled()) {
            throw new RuntimeException("La cuenta ya está verificada");
        }

        if (usuario.getVerificationCode() == null ||
            !usuario.getVerificationCode().equals(code)) {
            throw new RuntimeException("Código de verificación inválido");
        }

        if (usuario.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El código de verificación ha expirado");
        }

        // Verificar cuenta
        usuario.setEnabled(true);
        usuario.setVerificationCode(null);
        usuario.setVerificationCodeExpiresAt(null);
        repository.save(usuario);

        return true;
    }

    public void sendRecoveryCode(String email) {
        Usuario usuario = repository.findTopByEmailEquals(email);

        if (usuario == null) {
            // Por seguridad, no revelar que el usuario no existe
            return;
        }

        // Generar código de recuperación
        String recoveryCode = generateRecoveryCode();
        usuario.setRecoveryCode(recoveryCode);
        usuario.setRecoveryCodeExpiresAt(LocalDateTime.now().plusHours(1));
        repository.save(usuario);

        // Enviar email
        try {
            emailService.sendRecoveryEmail(usuario.getEmail(), recoveryCode);
        } catch (Exception e) {
            System.err.println("Error enviando email de recuperación: " + e.getMessage());
        }
    }

    public void resetPassword(String email, String code, String newPassword) {
        Usuario usuario = repository.findTopByEmailEquals(email);

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (usuario.getRecoveryCode() == null ||
            !usuario.getRecoveryCode().equals(code)) {
            throw new RuntimeException("Código de recuperación inválido");
        }

        if (usuario.getRecoveryCodeExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El código de recuperación ha expirado");
        }

        // Cambiar contraseña
        usuario.setPassword(seguridad.GetPasswordEncoder().encode(newPassword));
        usuario.setRecoveryCode(null);
        usuario.setRecoveryCodeExpiresAt(null);
        repository.save(usuario);
    }

    public void resendVerificationCode(String email) {
        Usuario usuario = repository.findTopByEmailEquals(email);

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (usuario.getEnabled()) {
            throw new RuntimeException("La cuenta ya está verificada");
        }

        // Generar nuevo código
        String verificationCode = generateVerificationCode();
        usuario.setVerificationCode(verificationCode);
        usuario.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(24));
        repository.save(usuario);

        // Reenviar email
        try {
            emailService.sendVerificationEmail(usuario.getEmail(), verificationCode);
        } catch (Exception e) {
            System.err.println("Error reenviando email: " + e.getMessage());
        }
    }

    private String generateVerificationCode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    private String generateRecoveryCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findTopByEmailEquals(username);
    }
}
