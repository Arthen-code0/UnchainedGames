package modelos.unchainedgames.controllers;


import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.PedidoCreateDTO;
import modelos.unchainedgames.models.Pedido;
import modelos.unchainedgames.services.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
@AllArgsConstructor
public class PedidoController {
    private PedidoService service;

    @GetMapping("/all")
    public List<Pedido> obtenerTodasPedido(){
        return service.obtenerTodosPedido();
    }

    @GetMapping("/{id}")
    public Pedido obtenerMecanicaPorId(@PathVariable Integer id){

        return service.obtenerPedidoPorId(id);
    }

    @PostMapping("/create")
    public void createPedido(@RequestBody PedidoCreateDTO dto) {
        service.createPedido(dto);
    }

    @PutMapping("/update/{id}")
    public void updatePedido(@PathVariable Integer id, @RequestBody PedidoCreateDTO dto) {
        service.updatePedido(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletePedido(@PathVariable Integer id) {
        service.deletePedido(id);
    }

}
