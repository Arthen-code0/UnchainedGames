package modelos.unchainedgames.repository;

import modelos.unchainedgames.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILanguageRepository extends JpaRepository<Language, Integer> {
}
