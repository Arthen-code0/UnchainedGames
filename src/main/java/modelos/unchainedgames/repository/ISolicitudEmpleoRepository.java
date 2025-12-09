package modelos.unchainedgames.repository;

import modelos.unchainedgames.models.SolicitudEmpleo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repositorio JPA para la entidad SolicitudEmpleo
// Integer = tipo de la clave primaria (id)
public interface ISolicitudEmpleoRepository extends JpaRepository<SolicitudEmpleo, Integer> {

    // Devuelve todas las solicitudes de un usuario concreto
    List<SolicitudEmpleo> findByUsuarioId(Integer usuarioId);

    // Comprueba si ya existe una solicitud para un usuario
    boolean existsByUsuarioId(Integer usuarioId);
}
