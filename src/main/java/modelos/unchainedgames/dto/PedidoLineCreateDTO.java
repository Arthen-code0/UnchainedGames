package modelos.unchainedgames.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoLineCreateDTO {

    private Integer amount;
    private Integer productId;
}
