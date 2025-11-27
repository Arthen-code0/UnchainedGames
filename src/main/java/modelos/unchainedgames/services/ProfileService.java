package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.ProfileCreateDTO;
import modelos.unchainedgames.models.Profile;
import modelos.unchainedgames.repository.IProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@AllArgsConstructor
public class ProfileService {

    private IProfileRepository repository;

    public List<Profile> obtenerTodosProfiles(){
        return repository.findAll();
    }

    public Profile obtenerProfilesPorId(@PathVariable Integer id){

        Profile profile = repository.findById(id).orElse(null);

        return profile;
    }

    public void createProfile(ProfileCreateDTO dto){

        Profile newProfile = new Profile();

        newProfile.setName(dto.getName());
        newProfile.setSurnames(dto.getSurnames());
        newProfile.setFotoPerfilLink(dto.getFotoPerfilLink());
        newProfile.setEmail(dto.getEmail());
        newProfile.setPhoneNumber(dto.getPhoneNumber());

        repository.save(newProfile);
    }

    public void updateProfile(Integer id, ProfileCreateDTO dto){

        Profile updatedProfile = repository.findById(id).orElse(null);

        updatedProfile.setName(dto.getName());
        updatedProfile.setSurnames(dto.getSurnames());
        updatedProfile.setFotoPerfilLink(dto.getFotoPerfilLink());
        updatedProfile.setEmail(dto.getEmail());
        updatedProfile.setPhoneNumber(dto.getPhoneNumber());

        repository.save(updatedProfile);
    }

    public void deleteProfile(Integer id){
        repository.deleteById(id);
    }
}
