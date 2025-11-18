package modelos.unchainedgames.repository;

import modelos.unchainedgames.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPedidoRepository extends JpaRepository<Pedido, Integer> {

}
