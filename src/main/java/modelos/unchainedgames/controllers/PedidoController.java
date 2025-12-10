package modelos.unchainedgames.controllers;

import lombok.RequiredArgsConstructor;
import modelos.unchainedgames.dto.PedidoCreateDTO;
import modelos.unchainedgames.dto.PedidoMostrarDTO;
import modelos.unchainedgames.services.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    // Crear pedido (desde carrito del front)
    @PostMapping("/create")
    public PedidoMostrarDTO createPedido(@RequestBody PedidoCreateDTO dto) {
        return pedidoService.createPedido(dto);
    }

    // Pedidos del usuario autenticado
    @GetMapping("/me")
    public List<PedidoMostrarDTO> getPedidosUsuarioActual() {
        return pedidoService.getPedidosUsuarioActual();
    }

    // Detalle de un pedido concreto
    @GetMapping("/{id}")
    public PedidoMostrarDTO getPedidoById(@PathVariable Integer id) {
        return pedidoService.getPedidoById(id);
    }
}
