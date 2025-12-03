package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.controllers.AddressController;
import modelos.unchainedgames.dto.AddressCreateDTO;
import modelos.unchainedgames.dto.DTOConverter;
import modelos.unchainedgames.models.Address;
import modelos.unchainedgames.repository.IAddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressService {

    private final IAddressRepository repository;
    private final DTOConverter converter;


    public AddressCreateDTO obtenerDireccionPorId(Integer id) {
        return repository.findById(id)
                .map(address -> converter.toDto(address, this::toMostrarDTO))
                .orElse(null);
    }

    public List<AddressCreateDTO> obtenerTodasDirecciones() {
        return converter.toDtoList(
                repository.findAll(),
                this::toMostrarDTO
        );
    }

    public AddressCreateDTO createAddress(AddressCreateDTO dto) {
        Address address = toEntity(dto);
        address = repository.save(address);
        return toMostrarDTO(address);
    }

    public AddressCreateDTO updateAddress(Integer id, AddressCreateDTO dto) {
        Address address = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        // Actualizar campos
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setPostalCode(dto.getPostalCode());

        address = repository.save(address);
        return toMostrarDTO(address);
    }

    public void deleteAddress(Integer id) {
        repository.deleteById(id);
    }

    // Métodos de conversión internos (pueden ser privados)
    private Address toEntity(AddressCreateDTO dto) {
        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setPostalCode(dto.getPostalCode());
        return address;
    }

    private AddressCreateDTO toMostrarDTO(Address address) {
        AddressCreateDTO dto = new AddressCreateDTO();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setPostalCode(address.getPostalCode());
        return dto;
    }
}