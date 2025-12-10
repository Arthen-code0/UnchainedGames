package modelos.unchainedgames.repository;

import modelos.unchainedgames.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByUsuarioIdOrderByDatetimeDesc(Integer usuarioId);
}
