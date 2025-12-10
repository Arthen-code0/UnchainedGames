package modelos.unchainedgames.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"usuario", "lineas"})
@EqualsAndHashCode
@Entity
@Table(name = "pedido", schema = "unchainedgames", catalog = "postgres")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "datetime")
    private Timestamp datetime;

    @Column(name = "status")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Usuario usuario;

    // 👇 NUEVO: un pedido tiene muchas líneas
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoLine> lineas = new ArrayList<>();
}
