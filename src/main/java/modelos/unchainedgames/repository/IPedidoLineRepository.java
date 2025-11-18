package modelos.unchainedgames.repository;

import modelos.unchainedgames.models.PedidoLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPedidoLineRepository extends JpaRepository<PedidoLine, Integer> {
}
