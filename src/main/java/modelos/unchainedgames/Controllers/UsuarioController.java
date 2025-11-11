package modelos.unchainedgames.Controllers;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.UsuarioCreateDTO;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.services.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuarioController {

    private UsuarioService service;

    @GetMapping("/all")
    public List<Usuario> obtenerTodosUsuarios(){
        return service.obtenerTodosUsuarios();
    }

    @GetMapping("/{id}")
    public Usuario obtenerUsuarioPorId(@PathVariable Integer id){
        return service.obtenerUsuarioPorId(id);
    }

    @PostMapping("/create")
    public void createUsuario(@RequestBody UsuarioCreateDTO dto) {
        service.createUsuario(dto);
    }

    @PutMapping("/update/{id}")
    public void updateUsuario(@PathVariable Integer id, @RequestBody UsuarioCreateDTO dto) {
        service.updateUsuario(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Integer id) {
        service.deleteUsuario(id);
    }
}