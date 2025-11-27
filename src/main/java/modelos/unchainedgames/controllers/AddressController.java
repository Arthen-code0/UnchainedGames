package modelos.unchainedgames.controllers;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.AddressCreateDTO;
import modelos.unchainedgames.models.Address;
import modelos.unchainedgames.services.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@AllArgsConstructor
public class AddressController {

    private AddressService service;

    @GetMapping("/all")
    public List<Address> obtenerTodasDirecciones(){
        return service.obtenerTodasDirecciones();
    }

    @GetMapping("/{id}")
    public Address obtenerDireccionPorId(@PathVariable Integer id){
        return service.obtenerDireccionPorId(id);
    }

    @GetMapping("/city/{city}")  // Endpoint para buscar por ciudad
    public ResponseEntity<List<Address>> obtenerDireccionesPorCiudad(@PathVariable String city){
        List<Address> addresses = service.obtenerDireccionesPorCiudad(city);
        return ResponseEntity.ok(addresses);
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