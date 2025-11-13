package modelos.unchainedgames.repository;

import modelos.unchainedgames.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface IReviewRepository extends JpaRepository<Review, Integer> {
}
