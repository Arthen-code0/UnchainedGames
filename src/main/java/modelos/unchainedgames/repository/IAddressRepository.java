package modelos.unchainedgames.repository;

import modelos.unchainedgames.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAddressRepository extends JpaRepository<Address, Integer> {
}
