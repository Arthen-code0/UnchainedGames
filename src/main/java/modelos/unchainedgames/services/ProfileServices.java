package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.ProfileCreateDTO;
import modelos.unchainedgames.models.Profile;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.repository.IProfileRepository;
import modelos.unchainedgames.repository.IUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProfileServices implements IProfileService {

    private IProfileRepository perfilRepository;
    private IUsuarioRepository usuarioRepository;

    public Profile buscarPorUsuario(Usuario usuario){
        return perfilRepository.findTopByUsuario(usuario);
    }

    public Profile guardarPerfil(Profile perfil){
        return perfilRepository.save(perfil);
    }

    public List<ProfileCreateDTO> getAll(){
        List<Profile> profiles = perfilRepository.findAll();
        List<ProfileCreateDTO> perfilDTOS = new ArrayList<>();

        for(Profile p : profiles){
            ProfileCreateDTO dto = new ProfileCreateDTO();
            dto.setId(p.getId());
            dto.setUsername(p.getUsername());
            dto.setName(p.getName());
            dto.setSurnames(p.getSurnames());
            dto.setFotoPerfilLink(p.getFotoPerfilLink());
            dto.setEmail(p.getEmail());
            dto.setPhoneNumber(p.getPhoneNumber());
            perfilDTOS.add(dto);
        }

        return perfilDTOS;
    }

    public ProfileCreateDTO updateProfile(Profile profileLoged, ProfileCreateDTO profileDTO) {
        profileLoged.setName(profileDTO.getName());
        profileLoged.setSurnames(profileDTO.getSurnames());
        profileLoged.setFotoPerfilLink(profileDTO.getFotoPerfilLink());
        profileLoged.setEmail(profileDTO.getEmail());
        profileLoged.setPhoneNumber(profileDTO.getPhoneNumber());

        // CORREGIDO: Usar la instancia del repositorio inyectado
        Profile perfilActualizado = perfilRepository.save(profileLoged);

        Usuario usuario = profileLoged.getUsuario();
        if (usuario != null) {
            usuario.setEmail(profileDTO.getEmail());
            // CORREGIDO: Usar la instancia del repositorio inyectado
            usuarioRepository.save(usuario);
        }

        return mapToDTO(perfilActualizado);
    }

    private ProfileCreateDTO mapToDTO(Profile profile) {
        ProfileCreateDTO dto = new ProfileCreateDTO();
        dto.setId(profile.getId());
        dto.setUsername(profile.getUsername());
        dto.setName(profile.getName());
        dto.setSurnames(profile.getSurnames());
        dto.setFotoPerfilLink(profile.getFotoPerfilLink());
        dto.setEmail(profile.getEmail());
        dto.setPhoneNumber(profile.getPhoneNumber());
        return dto;
    }
}