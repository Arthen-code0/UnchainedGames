package modelos.unchainedgames.repository;

import modelos.unchainedgames.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByProductIdOrderByDatetimeDesc(Integer productId);

    List<Review> findByUsuarioIdOrderByDatetimeDesc(Integer usuarioId);
}
