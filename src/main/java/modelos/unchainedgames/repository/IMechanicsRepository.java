package modelos.unchainedgames.repository;

import modelos.unchainedgames.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMechanicsRepository extends JpaRepository<Category, Integer> {


}
