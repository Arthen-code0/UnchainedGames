package modelos.unchainedgames.dto;

import lombok.Data;
import modelos.unchainedgames.models.Usuario;

import java.sql.Timestamp;

@Data
public class PedidoCreateDTO {
    private Integer id;
    private Timestamp datetime;
    private Integer status;
    private Usuario usuario;
}
