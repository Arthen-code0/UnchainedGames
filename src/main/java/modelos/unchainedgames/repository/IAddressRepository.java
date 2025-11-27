package modelos.unchainedgames.repository;

import modelos.unchainedgames.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IAddressRepository extends JpaRepository<Address, Integer> {

    @Query(value = "SELECT * FROM address a WHERE a.city = :city", nativeQuery = true)
    List<Address> obtenerTodasDireccionesPorCiudad(@Param("city") String city);

}
