package modelos.unchainedgames.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCreateDTO {

    private List<PedidoLineCreateDTO> lineas;
}
