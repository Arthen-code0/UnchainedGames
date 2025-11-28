// AuthController.java
package modelos.unchainedgames.controllers;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.AuthResponse;
import modelos.unchainedgames.dto.LoginRequest;
import modelos.unchainedgames.dto.UsuarioCreateDTO;
import modelos.unchainedgames.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
             return ResponseEntity.badRequest()
                    .body(new AuthResponse(null, null, "Error: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UsuarioCreateDTO dto) {
        try {
            AuthResponse response = authService.register(dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(null, null, "Error: " + e.getMessage()));
        }
    }
}