package modelos.unchainedgames.controllers;

import modelos.unchainedgames.listed.EstadoSolicitud;
import modelos.unchainedgames.models.SolicitudEmpleo;
import modelos.unchainedgames.services.SolicitudEmpleoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import modelos.unchainedgames.dto.SolicitudEmpleoDTO;

import java.util.List;

@RestController
@RequestMapping("/solicitudes-empleo")
public class SolicitudEmpleoController {

    private final SolicitudEmpleoService solicitudEmpleoService;

    public SolicitudEmpleoController(SolicitudEmpleoService solicitudEmpleoService) {
        this.solicitudEmpleoService = solicitudEmpleoService;
    }

    // DTO para crear solicitud
    public static class CrearSolicitudRequest {
        public Integer usuarioId;
        public String cvurl;   // mismo nombre que el campo de la entidad
        public String mensaje;
    }

    @PostMapping
    public ResponseEntity<SolicitudEmpleoDTO> crearSolicitud(@RequestBody CrearSolicitudRequest request) {
        return ResponseEntity.ok(
                solicitudEmpleoService.crearSolicitud(
                        request.usuarioId,
                        request.cvurl,
                        request.mensaje
                )
        );
    }


    @GetMapping
    public ResponseEntity<List<SolicitudEmpleoDTO>> listarTodas() {
        return ResponseEntity.ok(solicitudEmpleoService.listarTodas());
    }


    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<SolicitudEmpleoDTO>> listarPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(
                solicitudEmpleoService.listarPorUsuario(usuarioId)
        );
    }

    // Cambiar estado usando el ENUM directamente
    // Ejemplo de llamada: PATCH /solicitudes-empleo/1/estado?estado=PENDENTE
    @PatchMapping("/{id}/estado")
    public ResponseEntity<SolicitudEmpleoDTO> cambiarEstado(
            @PathVariable Integer id,
            @RequestParam EstadoSolicitud estado
    ) {
        return ResponseEntity.ok(
                solicitudEmpleoService.cambiarEstado(id, estado)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        solicitudEmpleoService.eliminarSolicitud(id);
        return ResponseEntity.noContent().build();
    }
}
