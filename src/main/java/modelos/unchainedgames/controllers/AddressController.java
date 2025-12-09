package modelos.unchainedgames.controllers;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.AddressCreateDTO;
import modelos.unchainedgames.services.AddressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@AllArgsConstructor
public class AddressController {

    private AddressService service;

    @GetMapping("/all")
    public List<AddressCreateDTO> obtenerTodasDirecciones(){
        return service.obtenerTodasDirecciones();
    }

    @GetMapping("/{id}")
    public AddressCreateDTO obtenerDireccionPorId(@PathVariable Integer id){
        return service.obtenerDireccionPorId(id);
    } 

    @PostMapping("/create")
    public void createAddress(@RequestBody AddressCreateDTO dto) {
        service.createAddress(dto);
    }

    @PutMapping("/update/{id}")
    public void updateAddress(@PathVariable Integer id, @RequestBody AddressCreateDTO dto) {
        service.updateAddress(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Integer id) {
        service.deleteAddress(id);
    }
}