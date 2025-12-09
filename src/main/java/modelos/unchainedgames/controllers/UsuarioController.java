package modelos.unchainedgames.controllers;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.LoginCreateDTO;
import modelos.unchainedgames.dto.UsuarioCreateDTO;
import modelos.unchainedgames.dto.UsuarioMostrarDTO;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.services.LoginService;
import modelos.unchainedgames.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    private UsuarioService service;
    private LoginService loginService;

    @GetMapping("/all")
    public List<UsuarioMostrarDTO> obtenerTodosUsuarios(){
        return service.obtenerTodosUsuarios();
    }

    @GetMapping("/{id}")
    public Usuario obtenerPedidosPorId(@PathVariable Integer id){
        return service.obtenerUsuariosPorId(id);
    }

    @PostMapping("/create")
    public void createUsuarios(@RequestBody UsuarioCreateDTO dto) {
        service.createUsuario(dto);
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
            // Manejar errores de autenticación
            return ResponseEntity.status(401)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            // Manejar otros errores
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }
}