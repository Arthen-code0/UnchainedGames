package modelos.unchainedgames.controllers;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.MechanicsCreateDTO;
import modelos.unchainedgames.models.Mechanics;
import modelos.unchainedgames.services.MechanicsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mechanics")
@AllArgsConstructor
public class MechanicsController {

    private MechanicsService service;

    @GetMapping("/all")
    public List<Mechanics> obtenerTodasMecanicas(){
        return service.obtenerTodasMecanicas();
    }

    @GetMapping("/{id}")
    public Mechanics obtenerMecanicaPorId(@PathVariable Integer id){
        return service.obtenerMecanicaPorId(id);
    }

    @PostMapping("/create")
    public void createMechanics(@RequestBody MechanicsCreateDTO dto) {
        service.createMechanics(dto);
    }

    @PutMapping("/update/{id}")
    public void updateMechanics(@PathVariable Integer id, @RequestBody MechanicsCreateDTO dto) {
        service.updateMechanics(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteMechanics(@PathVariable Integer id) {
        service.deleteMechanics(id);
    }
}