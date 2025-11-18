package modelos.unchainedgames.controllers;

import modelos.unchainedgames.dto.PedidoLineCreateDTO;
import modelos.unchainedgames.models.PedidoLine;
import modelos.unchainedgames.services.PedidoLineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class PedidoLineController {
    private PedidoLineService service;

    @GetMapping("/all")
    public List<PedidoLine> obtenerTodasPedidoLine(){
        return service.obtenerTodosPedidoLine();
    }

    @GetMapping("/{id}")
    public PedidoLine obtenerMecanicaPorId(@PathVariable Integer id){

        return service.obtenerPedidoLinePorId(id);
    }

    @PostMapping("/create")
    public void createPedidoLine(@RequestBody PedidoLineCreateDTO dto) {
        service.createPedidoLine(dto);
    }

    @PutMapping("/update/{id}")
    public void updatePedidoLine(@PathVariable Integer id, @RequestBody PedidoLineCreateDTO dto) {
        service.updatePedidoLine(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletePedidoLine(@PathVariable Integer id) {
        service.deletePedidoLine(id);
    }

}
