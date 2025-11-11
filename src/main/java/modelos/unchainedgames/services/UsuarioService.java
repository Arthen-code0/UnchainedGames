package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.UsuarioCreateDTO;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.repository.IUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

    private IUsuarioRepository repository;

    public List<Usuario> obtenerTodosUsuarios(){
        return repository.findAll();
    }

    public Usuario obtenerUsuarioPorId(Integer id){
        return repository.findById(id).orElse(null);
    }

    public void createUsuario(UsuarioCreateDTO dto){
        Usuario newUsuario = new Usuario();
        newUsuario.setName(dto.getName());
        newUsuario.setSurnames(dto.getSurnames());
        newUsuario.setPhoneNumber(dto.getPhoneNumber());
        newUsuario.setEmail(dto.getEmail());
        newUsuario.setPassword(dto.getPassword());
        newUsuario.setAddresses(dto.getAddresses());

        repository.save(newUsuario);
    }

    public void updateUsuario(Integer id, UsuarioCreateDTO dto){
        Usuario updatedUsuario = repository.findById(id).orElse(null);

        if (updatedUsuario != null){
            updatedUsuario.setName(dto.getName());
            updatedUsuario.setSurnames(dto.getSurnames());
            updatedUsuario.setPhoneNumber(dto.getPhoneNumber());
            updatedUsuario.setEmail(dto.getEmail());
            updatedUsuario.setPassword(dto.getPassword());
            updatedUsuario.setAddresses(dto.getAddresses());

            repository.save(updatedUsuario);
        }
    }

    public void deleteUsuario(Integer id){
        repository.deleteById(id);
    }
}