package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.AddressCreateDTO;
import modelos.unchainedgames.models.Address;
import modelos.unchainedgames.repository.IAddressRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AddressService {

    private IAddressRepository repository;

//    public List<Address> obtenerTodasDirecciones(){
//
//        List<Address> address = repository.findAll();
//        List<AddressCreateDTO> list = new ArrayList<>();
//        for(Address a : address){
//            AddressCreateDTO dto = new AddressCreateDTO();
//
//        }
//    }

    public List<Address> obtenerTodasDirecciones() {
        return repository.findAll();
    }

    public Address obtenerDireccionPorId(Integer id){
        return repository.findById(id).orElse(null);
    }

    public void createAddress(AddressCreateDTO dto){
        Address newAddress = new Address();

        newAddress.setPostalCode(dto.getPostalCode());
        newAddress.setStreet(dto.getStreet());
        newAddress.setNumber(dto.getNumber());
        newAddress.setFloor(dto.getFloor());
        newAddress.setCity(dto.getCity());
        newAddress.setProvince(dto.getProvince());
        newAddress.setUsuarios(dto.getUsuarios());

        repository.save(newAddress);
    }

    public void updateAddress(Integer id, AddressCreateDTO dto){
        Address updatedAddress = repository.findById(id).orElse(null);

        if (updatedAddress != null){
            updatedAddress.setPostalCode(dto.getPostalCode());
            updatedAddress.setStreet(dto.getStreet());
            updatedAddress.setNumber(dto.getNumber());
            updatedAddress.setFloor(dto.getFloor());
            updatedAddress.setCity(dto.getCity());
            updatedAddress.setProvince(dto.getProvince());
            updatedAddress.setUsuarios(dto.getUsuarios());

            repository.save(updatedAddress);
        }
    }

    public void deleteAddress(Integer id){
        repository.deleteById(id);
    }

    public List<Address> obtenerDireccionesPorCiudad(String city) {
        return repository.obtenerTodasDireccionesPorCiudad(city);
    }
}