package modelos.unchainedgames.dto;

import modelos.unchainedgames.listed.EstadoSolicitud;

import java.time.LocalDate;

/**
 * DTO = objeto plano para RESPONDER a la API.
 * No tiene relaciones, solo datos simples.
 */
public class SolicitudEmpleoDTO {

    private Integer id;

    private LocalDate datetime;

    private String cvurl;

    private String mensaje;

    private EstadoSolicitud estadoSolicitud;

    // SOLO el id del usuario, no el objeto entero
    private Integer usuarioId;

    // ===== Constructor =====
    public SolicitudEmpleoDTO() {}

    // ===== Getters / Setters =====

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDate datetime) {
        this.datetime = datetime;
    }

    public String getCvurl() {
        return cvurl;
    }

    public void setCvurl(String cvurl) {
        this.cvurl = cvurl;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public EstadoSolicitud getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(EstadoSolicitud estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}
