package modelos.unchainedgames.dto;

import lombok.Data;
import modelos.unchainedgames.models.PedidoLine;
import modelos.unchainedgames.models.Product;

import java.math.BigDecimal;

@Data
public class PedidoLineCreateDTO {
    private Integer id;
    private Integer amount;
    private BigDecimal pvp;
    private Product product;
    private PedidoLine pedidoLine;
}
