package modelos.unchainedgames.repository;

import modelos.unchainedgames.models.Profile;
import modelos.unchainedgames.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProfileRepository extends JpaRepository<Profile, Integer> {

//    Profile findTopByUsuario(Usuario usuario);
//    Profile findByUsuarioId(Integer usuarioId);
}