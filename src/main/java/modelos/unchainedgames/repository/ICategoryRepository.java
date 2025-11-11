package modelos.unchainedgames.repository;

import modelos.unchainedgames.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Integer> {
}
