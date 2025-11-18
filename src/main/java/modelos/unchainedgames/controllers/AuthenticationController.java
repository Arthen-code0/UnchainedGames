package modelos.unchainedgames.controllers;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.UsuarioCreateDTO;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/authentication")
@AllArgsConstructor
public class AuthenticationController {

    private final UsuarioService usuarioService;

    // Endpoint de Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Usuario usuario = usuarioService.loginUsuario(loginRequest.getUsername(), loginRequest.getPassword());

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Login exitoso");
            response.put("usuario", usuario);
            response.put("id", usuario.getId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Credenciales inválidas");
            errorResponse.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    // Endpoint de Registro
    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody UsuarioCreateDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioService.registroUsuario(usuarioDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Usuario registrado exitosamente. Se ha enviado un código de verificación a su email.");
            response.put("usuarioId", usuario.getId());
            response.put("email", usuario.getEmail());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error en el registro");
            errorResponse.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // Endpoint para verificar cuenta
    @PostMapping("/verificar")
    public ResponseEntity<?> verificarCuenta(@RequestBody VerificationRequest verificationRequest) {
        try {
            boolean verificado = usuarioService.verificarCuenta(
                    verificationRequest.getEmail(),
                    verificationRequest.getCodigoVerificacion()
            );

            if (verificado) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Cuenta verificada exitosamente");
                return ResponseEntity.ok(response);
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Código de verificación inválido");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error en la verificación");
            errorResponse.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // Endpoint para enviar código de recuperación
    @PostMapping("/recuperacion/enviar-codigo")
    public ResponseEntity<?> enviarCodigoRecuperacion(@RequestBody RecoveryCodeRequest recoveryRequest) {
        try {
            usuarioService.enviarCodigoRecuperacion(recoveryRequest.getEmail());

            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Código de recuperación enviado a su email");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error enviando código de recuperación");
            errorResponse.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // Endpoint para cambiar contraseña con código de recuperación
    @PostMapping("/recuperacion/cambiar-contrasena")
    public ResponseEntity<?> cambiarContrasenaConCodigo(@RequestBody ChangePasswordWithCodeRequest changeRequest) {
        try {
            usuarioService.cambiarContrasenaConCodigo(
                    changeRequest.getEmail(),
                    changeRequest.getCodigoRecuperacion(),
                    changeRequest.getNuevaContrasena()
            );

            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Contraseña cambiada exitosamente");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error cambiando contraseña");
            errorResponse.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // Endpoint para cambiar contraseña (usuario autenticado)
    @PostMapping("/cambiar-contrasena")
    public ResponseEntity<?> cambiarContrasena(@RequestBody ChangePasswordRequest changeRequest) {
        try {
            Optional<Usuario> usuarioActual = usuarioService.getCurrentUser();

            if (usuarioActual.isEmpty()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Usuario no autenticado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }

            usuarioService.cambiarContrasena(
                    usuarioActual.get().getUsername(),
                    changeRequest.getContrasenaActual(),
                    changeRequest.getNuevaContrasena()
            );

            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Contraseña cambiada exitosamente");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error cambiando contraseña");
            errorResponse.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // Endpoint para obtener usuario actual
    @GetMapping("/usuario-actual")
    public ResponseEntity<?> obtenerUsuarioActual() {
        try {
            Optional<Usuario> usuario = usuarioService.getCurrentUser();

            if (usuario.isPresent()) {
                return ResponseEntity.ok(usuario.get());
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "No hay usuario autenticado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error obteniendo usuario actual");
            errorResponse.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Clases DTO para las requests
    public static class LoginRequest {
        private String username;
        private String password;

        // Getters y Setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class VerificationRequest {
        private String email;
        private String codigoVerificacion;

        // Getters y Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getCodigoVerificacion() { return codigoVerificacion; }
        public void setCodigoVerificacion(String codigoVerificacion) { this.codigoVerificacion = codigoVerificacion; }
    }

    public static class RecoveryCodeRequest {
        private String email;

        // Getters y Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    public static class ChangePasswordWithCodeRequest {
        private String email;
        private String codigoRecuperacion;
        private String nuevaContrasena;

        // Getters y Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getCodigoRecuperacion() { return codigoRecuperacion; }
        public void setCodigoRecuperacion(String codigoRecuperacion) { this.codigoRecuperacion = codigoRecuperacion; }
        public String getNuevaContrasena() { return nuevaContrasena; }
        public void setNuevaContrasena(String nuevaContrasena) { this.nuevaContrasena = nuevaContrasena; }
    }

    public static class ChangePasswordRequest {
        private String contrasenaActual;
        private String nuevaContrasena;

        // Getters y Setters
        public String getContrasenaActual() { return contrasenaActual; }
        public void setContrasenaActual(String contrasenaActual) { this.contrasenaActual = contrasenaActual; }
        public String getNuevaContrasena() { return nuevaContrasena; }
        public void setNuevaContrasena(String nuevaContrasena) { this.nuevaContrasena = nuevaContrasena; }
    }
}