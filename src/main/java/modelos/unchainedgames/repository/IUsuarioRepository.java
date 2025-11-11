package modelos.unchainedgames.repository;

import modelos.unchainedgames.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
}
