package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.LanguageCreateDTO;
import modelos.unchainedgames.models.Language;
import modelos.unchainedgames.repository.ILanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LanguageService {

    private ILanguageRepository repository;

    public List<Language> obtenerTodosLenguajes(){
        return repository.findAll();
    }

    public Language obtenerLenguajePorId(Integer id){
        return repository.findById(id).orElse(null);
    }

    public void createLanguage(LanguageCreateDTO dto){
        Language newLanguage = new Language();
        newLanguage.setName(dto.getName());
        newLanguage.setProducts(dto.getProducts());

        repository.save(newLanguage);
    }

    public void updateLanguage(Integer id, LanguageCreateDTO dto){
        Language updatedLanguage = repository.findById(id).orElse(null);

        if (updatedLanguage != null){
            updatedLanguage.setName(dto.getName());
            updatedLanguage.setProducts(dto.getProducts());

            repository.save(updatedLanguage);
        }
    }

    public void deleteLanguage(Integer id){
        repository.deleteById(id);
    }
}