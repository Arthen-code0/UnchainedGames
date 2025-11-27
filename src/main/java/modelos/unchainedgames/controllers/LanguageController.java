package modelos.unchainedgames.controllers;

import lombok.*;
import modelos.unchainedgames.dto.LanguageCreateDTO;
import modelos.unchainedgames.models.Language;
import modelos.unchainedgames.services.LanguageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/language")
@AllArgsConstructor
public class LanguageController {

    private LanguageService service;

    @GetMapping("/all")
    public List<Language> obtenerTodosLenguajes(){
        return service.obtenerTodosLenguajes();
    }

    @GetMapping("/{id}")
    public Language obtenerLenguajePorId(@PathVariable Integer id){
        return service.obtenerLenguajePorId(id);
    }

    @PostMapping("/create")
    public void createLanguage(@RequestBody LanguageCreateDTO dto) {
        service.createLanguage(dto);
    }

    @PutMapping("/update/{id}")
    public void updateLanguage(@PathVariable Integer id, @RequestBody LanguageCreateDTO dto) {
        service.updateLanguage(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteLanguage(@PathVariable Integer id) {
        service.deleteLanguage(id);
    }
}