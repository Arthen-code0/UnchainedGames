package modelos.unchainedgames.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoMostrarDTO {

    private Integer id;
    private Timestamp datetime;
    private Integer status;
    private Double total;

    private Integer usuarioId;
    private String usuarioName;

    private List<PedidoLineMostrarDTO> lineas;
}
