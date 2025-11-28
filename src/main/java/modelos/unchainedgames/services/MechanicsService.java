package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.MechanicsCreateDTO;
import modelos.unchainedgames.models.Mechanics;
import modelos.unchainedgames.repository.IMechanicsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MechanicsService {

    private IMechanicsRepository repository;

    public List<Mechanics> obtenerTodasMecanicas(){
        return repository.findAll();
    }

    public Mechanics obtenerMecanicaPorId(Integer id){
        return repository.findById(id).orElse(null);
    }

    public void createMechanics(MechanicsCreateDTO dto){
        Mechanics newMechanics = new Mechanics();
        newMechanics.setName(dto.getName());

        repository.save(newMechanics);
    }

    public void updateMechanics(Integer id, MechanicsCreateDTO dto){
        Mechanics updatedMechanics = repository.findById(id).orElse(null);

        if (updatedMechanics != null){
            updatedMechanics.setName(dto.getName());

            repository.save(updatedMechanics);
        }
    }

    public void deleteMechanics(Integer id){
        repository.deleteById(id);
    }
}