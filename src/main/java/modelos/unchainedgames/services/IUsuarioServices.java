package modelos.unchainedgames.services;

import modelos.unchainedgames.dto.UsuarioCreateDTO;
import modelos.unchainedgames.models.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioServices {
    List<Usuario> obtenerTodosUsuarios();
    Optional<Usuario> obtenerUsuarioPorId(Integer id);
    void createUsuario(UsuarioCreateDTO dto);
    void updateUsuario(Integer id, UsuarioCreateDTO dto);
    void deleteUsuario(Integer id);
}