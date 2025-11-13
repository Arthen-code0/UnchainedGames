package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.UsuarioCreateDTO;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.repository.IUsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService implements IUsuarioServices, UserDetailsService {

    private IUsuarioRepository repository;

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
        Usuario newUsuario = new Usuario();
        newUsuario.setName(dto.getName());
        newUsuario.setSurnames(dto.getSurnames());
        newUsuario.setPhoneNumber(dto.getPhoneNumber());
        newUsuario.setEmail(dto.getEmail());
        newUsuario.setPassword(dto.getPassword());
        newUsuario.setAddresses(dto.getAddresses());
        repository.save(newUsuario);
    }

    @Override
    public void updateUsuario(Integer id, UsuarioCreateDTO dto) {
        repository.findById(id).ifPresent(usuario -> {
            usuario.setName(dto.getName());
            usuario.setSurnames(dto.getSurnames());
            usuario.setPhoneNumber(dto.getPhoneNumber());
            usuario.setEmail(dto.getEmail());
            usuario.setPassword(dto.getPassword());
            usuario.setAddresses(dto.getAddresses());
            repository.save(usuario);
        });
    }

    @Override
    public void deleteUsuario(Integer id) {
        repository.deleteById(id);
    }
}
