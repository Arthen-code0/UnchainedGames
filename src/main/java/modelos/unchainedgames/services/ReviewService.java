package modelos.unchainedgames.services;

import lombok.RequiredArgsConstructor;
import modelos.unchainedgames.dto.ReviewCreateDTO;
import modelos.unchainedgames.dto.ReviewMostrarDTO;
import modelos.unchainedgames.mapper.ReviewMapper;
import modelos.unchainedgames.models.Product;
import modelos.unchainedgames.models.Review;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.repository.IProductRepository;
import modelos.unchainedgames.repository.IReviewRepository;
import modelos.unchainedgames.repository.IUsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final IReviewRepository reviewRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IProductRepository productRepository;

    // 🔹 Crear reseña para el producto indicado
    public ReviewMostrarDTO createReview(ReviewCreateDTO dto) {

        // Usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }

        String email = auth.getName();
        Usuario usuario = usuarioRepository.findTopByEmailEquals(email);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Review review = new Review();
        review.setScore(dto.getScore());
        review.setDescription(dto.getDescription());
        review.setDatetime(LocalDate.now());
        review.setUsuario(usuario);
        review.setProduct(product);

        Review saved = reviewRepository.save(review);

        return ReviewMapper.toMostrarDTO(saved);
    }

    // 🔹 Listar reseñas de un producto
    public List<ReviewMostrarDTO> getReviewsByProduct(Integer productId) {
        return reviewRepository.findByProductIdOrderByDatetimeDesc(productId)
                .stream()
                .map(ReviewMapper::toMostrarDTO)
                .toList();
    }

    // 🔹 Listar todas las reviews
    public List<ReviewMostrarDTO> obtenerTodasReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(ReviewMapper::toMostrarDTO)
                .toList();
    }

    // 🔹 Obtener una review por id
    public ReviewMostrarDTO obtenerReviewPorId(Integer id) {
        Review r = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review no encontrada"));
        return ReviewMapper.toMostrarDTO(r);
    }

    // 🔹 Actualizar review existente
    public ReviewMostrarDTO updateReview(Integer id, ReviewCreateDTO dto) {
        Review r = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review no encontrada"));

        r.setScore(dto.getScore());
        r.setDescription(dto.getDescription());
        // Si quieres actualizar la fecha:
        // r.setDatetime(LocalDate.now());

        Review updated = reviewRepository.save(r);
        return ReviewMapper.toMostrarDTO(updated);
    }

    // 🔹 Borrar review
    public void deleteReview(Integer id) {
        reviewRepository.deleteById(id);
    }

    // 🔹 Reviews del usuario logueado
    public List<ReviewMostrarDTO> getReviewsByCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }

        String email = auth.getName();
        Usuario usuario = usuarioRepository.findTopByEmailEquals(email);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        return reviewRepository.findByUsuarioIdOrderByDatetimeDesc(usuario.getId())
                .stream()
                .map(ReviewMapper::toMostrarDTO)
                .toList();
    }
}
