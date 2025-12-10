package modelos.unchainedgames.controllers;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.LoginCreateDTO;
import modelos.unchainedgames.dto.UsuarioCreateDTO;
import modelos.unchainedgames.dto.UsuarioMostrarDTO;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.services.UsuarioService;
import modelos.unchainedgames.services.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    private final UsuarioService service;
    private final LoginService loginService;

    @GetMapping("/all")
    public List<UsuarioMostrarDTO> obtenerTodosUsuarios(){
        return service.obtenerTodosUsuarios();
    }

    @GetMapping("/{id}")
    public Usuario obtenerPedidosPorId(@PathVariable Integer id){
        return service.obtenerUsuariosPorId(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUsuarios(@RequestBody UsuarioCreateDTO dto) {
        try {
            service.createUsuario(dto);
            return ResponseEntity.ok(Map.of(
                "message", "Usuario registrado exitosamente. Revisa tu correo para verificar tu cuenta."
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400)
                .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String code = request.get("code");
            service.verifyAccount(email, code);
            return ResponseEntity.ok(Map.of(
                "message", "Cuenta verificada exitosamente. Ya puedes iniciar sesión."
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400)
                .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<?> resendVerification(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            service.resendVerificationCode(email);
            return ResponseEntity.ok(Map.of(
                "message", "Código de verificación reenviado. Revisa tu correo."
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400)
                .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/request-recovery")
    public ResponseEntity<?> requestRecovery(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        service.sendRecoveryCode(email);
        return ResponseEntity.ok(Map.of(
            "message", "Si el email existe, recibirás un código de recuperación."
        ));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String code = request.get("code");
            String newPassword = request.get("newPassword");
            service.resetPassword(email, code, newPassword);
            return ResponseEntity.ok(Map.of(
                "message", "Contraseña restablecida exitosamente."
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400)
                .body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public void updateUsuarios(@PathVariable Integer id, @RequestBody UsuarioCreateDTO dto) {
        service.updateUsuario(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuarios(@PathVariable Integer id) {
        service.deleteUsuario(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCreateDTO dto) {
        try {
            Map<String, Object> respuesta = loginService.loguearUsuario(dto);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }
}
