package modelos.unchainedgames.services;

import lombok.RequiredArgsConstructor;
import modelos.unchainedgames.dto.ReviewCreateDTO;
import modelos.unchainedgames.dto.ReviewMostrarDTO;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final IReviewRepository reviewRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IProductRepository productRepository;

    // 🔹 Crear reseña para el producto indicado
    public void createReview(ReviewCreateDTO dto) {

        // Obtenemos el usuario logueado a partir del SecurityContext
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }

        String email = auth.getName(); // getName() devuelve el username (tu Usuario.getUsername() = email)
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

        reviewRepository.save(review);
    }

    // 🔹 Listar reseñas de un producto
    public List<ReviewMostrarDTO> getReviewsByProduct(Integer productId) {

        List<Review> reviews = reviewRepository.findByProductIdOrderByDatetimeDesc(productId);

        return reviews.stream()
                .map(this::toMostrarDTO)
                .collect(Collectors.toList());
    }

    private ReviewMostrarDTO toMostrarDTO(Review review) {
        ReviewMostrarDTO dto = new ReviewMostrarDTO();
        dto.setId(review.getId());
        dto.setScore(review.getScore());
        dto.setDescription(review.getDescription());
        dto.setDatetime(review.getDatetime());

        Usuario u = review.getUsuario();
        dto.setUsuarioId(u.getId());
        dto.setUsuarioName(u.getName() + " " + u.getSurnames());

        dto.setProductId(review.getProduct().getId());
        dto.setProductName(review.getProduct().getName());

        return dto;
    }

    // (Opcionales) listar todas, obtener por id, etc. si te interesa

    public List<ReviewMostrarDTO> obtenerTodosProductos() {
        return reviewRepository.findAll()
                .stream()
                .map(this::toMostrarDTO)
                .collect(Collectors.toList());
    }

    public ReviewMostrarDTO obtenerProductosPorId(Integer id) {
        Review r = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review no encontrada"));
        return toMostrarDTO(r);
    }

    public void updateReview(Integer id, ReviewCreateDTO dto) {
        Review r = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review no encontrada"));

        r.setScore(dto.getScore());
        r.setDescription(dto.getDescription());
        // datetime podría quedarse igual o actualizarse:
        // r.setDatetime(LocalDate.now());

        reviewRepository.save(r);
    }

    public void deleteReview(Integer id) {
        reviewRepository.deleteById(id);
    }

    public List<ReviewMostrarDTO> getReviewsByCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }

        String email = auth.getName(); // tu Usuario.getUsername() devuelve email
        Usuario usuario = usuarioRepository.findTopByEmailEquals(email);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        List<Review> reviews =
                reviewRepository.findByUsuarioIdOrderByDatetimeDesc(usuario.getId());

        return reviews.stream()
                .map(this::toMostrarDTO)
                .collect(Collectors.toList());
    }
}
