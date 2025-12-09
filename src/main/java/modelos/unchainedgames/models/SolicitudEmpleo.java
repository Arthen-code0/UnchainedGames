package modelos.unchainedgames.models;

import jakarta.persistence.*;
import lombok.*;
import modelos.unchainedgames.listed.EstadoSolicitud;
import modelos.unchainedgames.listed.Rol;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString

@Entity
@Table(name = "solicitudempleo", schema = "unchainedgames", catalog = "postgres")
public class SolicitudEmpleo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "datetime")
    private LocalDate datetime;

    @Column(name = "cvurl")
    private String cvurl;

    @Column(name = "mensaje", length = 500)
    private String mensaje;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private EstadoSolicitud estadoSolicitud = EstadoSolicitud.PENDENTE;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
