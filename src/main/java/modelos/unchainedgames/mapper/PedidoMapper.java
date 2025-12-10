package modelos.unchainedgames.mapper;

import modelos.unchainedgames.dto.PedidoLineMostrarDTO;
import modelos.unchainedgames.dto.PedidoMostrarDTO;
import modelos.unchainedgames.models.Pedido;
import modelos.unchainedgames.models.PedidoLine;
import modelos.unchainedgames.models.Product;
import modelos.unchainedgames.models.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoMapper {

    public static PedidoLineMostrarDTO toLineaMostrarDTO(PedidoLine linea) {
        if (linea == null) return null;

        Product p = linea.getProduct();

        Double unitPrice = p != null ? p.getPrice() : 0.0;
        Double lineTotal = unitPrice * linea.getAmount();

        PedidoLineMostrarDTO dto = new PedidoLineMostrarDTO();
        dto.setId(linea.getId());
        dto.setAmount(linea.getAmount());
        dto.setUnitPrice(unitPrice);
        dto.setLineTotal(lineTotal);

        if (p != null) {
            dto.setProductId(p.getId());
            dto.setProductName(p.getName());
            dto.setProductPicture(p.getPicture());
        }

        return dto;
    }

    public static PedidoMostrarDTO toPedidoMostrarDTO(Pedido pedido) {
        if (pedido == null) return null;

        PedidoMostrarDTO dto = new PedidoMostrarDTO();
        dto.setId(pedido.getId());
        dto.setDatetime(pedido.getDatetime());
        dto.setStatus(pedido.getStatus());

        Usuario u = pedido.getUsuario();
        if (u != null) {
            dto.setUsuarioId(u.getId());
            dto.setUsuarioName(u.getName() + " " + u.getSurnames());
        }

        List<PedidoLineMostrarDTO> lineasDTO =
                pedido.getLineas()
                        .stream()
                        .map(PedidoMapper::toLineaMostrarDTO)
                        .collect(Collectors.toList());

        dto.setLineas(lineasDTO);

        Double total = lineasDTO.stream()
                .mapToDouble(PedidoLineMostrarDTO::getLineTotal)
                .sum();

        dto.setTotal(total);

        return dto;
    }
}
