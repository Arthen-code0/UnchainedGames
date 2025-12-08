package modelos.unchainedgames.services;

import lombok.RequiredArgsConstructor;
import modelos.unchainedgames.dto.UsuarioCreateDTO;
import modelos.unchainedgames.dto.UsuarioMostrarDTO;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.repository.IUsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final IUsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    //MÉTODOS DE NEGOCIO

    public List<UsuarioMostrarDTO> obtenerTodosUsuarios() {
        List<Usuario> usuarios = repository.findAll();
        List<UsuarioMostrarDTO> dtos = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            UsuarioMostrarDTO dto = new UsuarioMostrarDTO();
            dto.setId(usuario.getId());
            dto.setRol(usuario.getRol());
            dto.setEmail(usuario.getEmail());
            dto.setName(usuario.getName());
            dto.setSurnames(usuario.getSurnames());
            dto.setPhoneNumber(usuario.getPhoneNumber());
            dto.setEnabled(usuario.getEnabled());

            //ESTO FALTABA: AÑADIR EL DTO A LA LISTA
            dtos.add(dto);
        }

        return dtos;
    }

    public Usuario obtenerUsuariosPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void createUsuario(UsuarioCreateDTO dto) {

        Usuario newUsuario = new Usuario();

        newUsuario.setName(dto.getName());
        newUsuario.setSurnames(dto.getSurnames());
        newUsuario.setPhoneNumber(dto.getPhoneNumber());
        newUsuario.setEmail(dto.getEmail());
        //ENCRIPTA LA CONTRASEÑA AL CREAR
        newUsuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUsuario.setAddresses(dto.getAddresses());
        newUsuario.setEnabled(true);

        repository.save(newUsuario);
    }

    public void updateUsuario(Integer id, UsuarioCreateDTO dto) {

        Usuario updateUsuario = repository.findById(id).orElse(null);

        if (updateUsuario != null) {
            updateUsuario.setName(dto.getName());
            updateUsuario.setSurnames(dto.getSurnames());
            updateUsuario.setPhoneNumber(dto.getPhoneNumber());
            updateUsuario.setEmail(dto.getEmail());
            updateUsuario.setAddresses(dto.getAddresses());
            updateUsuario.setEnabled(true);

            // SI VIENE UNA CONTRASEÑA, LA ACTUALIZA ENCRIPTADA
            if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
                updateUsuario.setPassword(passwordEncoder.encode(dto.getPassword()));
            }

            repository.save(updateUsuario);
        }
    }

    public void deleteUsuario(Integer id) {
        repository.deleteById(id);
    }

    //USERDETAILSSERVICE (LOGIN POR EMAIL)

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username = email
        Usuario usuario = repository.findTopByEmailEquals(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + username);
        }
        return usuario; // Usuario implementa UserDetails
    }
}
