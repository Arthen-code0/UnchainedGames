package modelos.unchainedgames.dto;

import lombok.*;
import modelos.unchainedgames.models.Usuario;

import java.sql.Timestamp;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCreateDTO {
    private Integer id;
    private Timestamp datetime;
    private Integer status;
    private Usuario usuario;
}
