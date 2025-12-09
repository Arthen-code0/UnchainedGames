package modelos.unchainedgames.services;

import modelos.unchainedgames.models.SolicitudEmpleo;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.repository.ISolicitudEmpleoRepository;
import modelos.unchainedgames.repository.IUsuarioRepository;
import modelos.unchainedgames.listed.EstadoSolicitud;
import org.springframework.stereotype.Service;
import modelos.unchainedgames.dto.SolicitudEmpleoDTO;


import java.time.LocalDate;
import java.util.List;

@Service
public class SolicitudEmpleoService {

    // Repositorio de solicitudes
    private final ISolicitudEmpleoRepository solicitudEmpleoRepository;

    // Repositorio de usuarios
    private final IUsuarioRepository usuarioRepository;

    public SolicitudEmpleoService(ISolicitudEmpleoRepository solicitudEmpleoRepository,
                                  IUsuarioRepository usuarioRepository) {
        this.solicitudEmpleoRepository = solicitudEmpleoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Un usuario solo puede tener UNA solicitud de empleo.
     */
    public SolicitudEmpleoDTO crearSolicitud(Integer usuarioId, String cvurl, String mensaje) {

        if (solicitudEmpleoRepository.existsByUsuarioId(usuarioId)) {
            throw new IllegalStateException("El usuario ya tiene una solicitud.");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        SolicitudEmpleo solicitud = new SolicitudEmpleo();
        solicitud.setUsuario(usuario);
        solicitud.setCvurl(cvurl);
        solicitud.setMensaje(mensaje);
        solicitud.setDatetime(LocalDate.now());

        SolicitudEmpleo saved = solicitudEmpleoRepository.save(solicitud);

        return mapToDTO(saved);
    }


    public List<SolicitudEmpleoDTO> listarTodas() {
        return solicitudEmpleoRepository
                .findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }


    public List<SolicitudEmpleoDTO> listarPorUsuario(Integer usuarioId) {
        return solicitudEmpleoRepository
                .findByUsuarioId(usuarioId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }


    /**
     * Cambiar el estado usando el ENUM EstadoSolicitud.
     */
    public SolicitudEmpleoDTO cambiarEstado(Integer id, EstadoSolicitud nuevoEstado) {

        SolicitudEmpleo solicitud = solicitudEmpleoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        solicitud.setEstadoSolicitud(nuevoEstado);

        SolicitudEmpleo updated = solicitudEmpleoRepository.save(solicitud);

        return mapToDTO(updated);
    }

    public void eliminarSolicitud(Integer solicitudId) {

        if (!solicitudEmpleoRepository.existsById(solicitudId)) {
            throw new IllegalArgumentException("Solicitud no encontrada");
        }

        solicitudEmpleoRepository.deleteById(solicitudId);
    }


    private SolicitudEmpleoDTO mapToDTO(SolicitudEmpleo solicitud) {

        SolicitudEmpleoDTO dto = new SolicitudEmpleoDTO();

        dto.setId(solicitud.getId());
        dto.setDatetime(solicitud.getDatetime());
        dto.setCvurl(solicitud.getCvurl());
        dto.setMensaje(solicitud.getMensaje());
        dto.setEstadoSolicitud(solicitud.getEstadoSolicitud());

        // De la relación solo exponemos el ID
        dto.setUsuarioId(solicitud.getUsuario().getId());

        return dto;
    }

}
