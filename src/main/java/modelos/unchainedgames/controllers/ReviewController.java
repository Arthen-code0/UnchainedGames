package modelos.unchainedgames.controllers;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.ReviewCreateDTO;
import modelos.unchainedgames.dto.ReviewMostrarDTO;
import modelos.unchainedgames.services.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService service;

    // 🔹 Todas las reviews (opcional, más para pruebas)
    @GetMapping("/all")
    public List<ReviewMostrarDTO> obtenerTodasReviews() {
        return service.obtenerTodosProductos();
    }

    // 🔹 Reviews de un producto concreto
    @GetMapping("/product/{productId}")
    public List<ReviewMostrarDTO> obtenerReviewsPorProducto(@PathVariable Integer productId) {
        return service.getReviewsByProduct(productId);
    }

    // 🔹 Obtener review por id (opcional)
    @GetMapping("/{id}")
    public ReviewMostrarDTO obtenerReviewPorId(@PathVariable Integer id) {
        return service.obtenerProductosPorId(id);
    }

    // 🔹 Crear review (usuario sacado del token)
    @PostMapping("/create")
    public void createReview(@RequestBody ReviewCreateDTO dto) {
        service.createReview(dto);
    }

    // 🔹 Editar review (si lo quieres permitir)
    @PutMapping("/update/{id}")
    public void updateReview(@PathVariable Integer id, @RequestBody ReviewCreateDTO dto) {
        service.updateReview(id, dto);
    }

    // 🔹 Borrar review
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Integer id) {
        service.deleteReview(id);
    }

    @GetMapping("/me")
    public List<ReviewMostrarDTO> obtenerReviewsUsuarioActual() {
        return service.getReviewsByCurrentUser();
    }
}
