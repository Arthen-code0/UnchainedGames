package modelos.unchainedgames.controllers;

import modelos.unchainedgames.dto.ReviewCreateDTO;
import modelos.unchainedgames.models.Review;
import modelos.unchainedgames.services.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private ReviewService service;

    @GetMapping("/all")
    public List<Review> obtenerTodosPedidos(){
        return service.obtenerTodosProductos();
    }

    @GetMapping("/{id}")
    public Review obtenerPedidosPorId(@PathVariable Integer id){
        return service.obtenerProductosPorId(id);
    }

    @PostMapping("/create")
    public void createReview(@RequestBody ReviewCreateDTO dto) {
        service.createReview(dto);
    }

    @PutMapping("/update/{id}")
    public void updateReview(@PathVariable Integer id, @RequestBody ReviewCreateDTO dto) {
        service.updateReview(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Integer id) {
        service.deleteReview(id);
    }
}
