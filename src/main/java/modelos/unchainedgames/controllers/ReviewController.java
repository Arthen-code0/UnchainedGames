package modelos.unchainedgames.controllers;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.ReviewCreateDTO;
import modelos.unchainedgames.dto.ReviewMostrarDTO;
import modelos.unchainedgames.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService service;

    // 🔹 Todas las reviews
    @GetMapping("/all")
    public ResponseEntity<List<ReviewMostrarDTO>> obtenerTodasReviews() {
        return ResponseEntity.ok(service.obtenerTodasReviews());
    }

    // 🔹 Reviews de un producto concreto
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewMostrarDTO>> obtenerReviewsPorProducto(@PathVariable Integer productId) {
        return ResponseEntity.ok(service.getReviewsByProduct(productId));
    }

    // 🔹 Obtener review por id
    @GetMapping("/{id}")
    public ResponseEntity<ReviewMostrarDTO> obtenerReviewPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerReviewPorId(id));
    }

    // 🔹 Crear review
    @PostMapping("/create")
    public ResponseEntity<ReviewMostrarDTO> createReview(@RequestBody ReviewCreateDTO dto) {
        ReviewMostrarDTO created = service.createReview(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // 🔹 Editar review
    @PutMapping("/update/{id}")
    public ResponseEntity<ReviewMostrarDTO> updateReview(@PathVariable Integer id,
                                                         @RequestBody ReviewCreateDTO dto) {
        ReviewMostrarDTO updated = service.updateReview(id, dto);
        return ResponseEntity.ok(updated);
    }

    // 🔹 Borrar review
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        service.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Reviews del usuario actual
    @GetMapping("/me")
    public ResponseEntity<List<ReviewMostrarDTO>> obtenerReviewsUsuarioActual() {
        return ResponseEntity.ok(service.getReviewsByCurrentUser());
    }
}
