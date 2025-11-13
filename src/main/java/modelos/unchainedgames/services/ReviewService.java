package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.ReviewCreateDTO;
import modelos.unchainedgames.models.Review;
import modelos.unchainedgames.repository.IReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private IReviewRepository repository;

    public List<Review> obtenerTodosProductos(){
        return repository.findAll();
    }

    public Review obtenerProductosPorId(@PathVariable Integer id){

        Review review = repository.findById(id).orElse(null);

        return review;
    }

    public void createReview(ReviewCreateDTO dto){

        Review newReview = new Review();

        newReview.setScore(dto.getScore());
        newReview.setDescription(dto.getDescription());
        newReview.setDatetime(dto.getLocaldate());
        newReview.setProduct(dto.getProduct());
        newReview.setUsuario(dto.getUsuario());

        repository.save(newReview);
    }

    public void updateReview(Integer id, ReviewCreateDTO dto){

        Review updateReview = repository.findById(id).orElse(null);

        updateReview.setScore(dto.getScore());
        updateReview.setDescription(dto.getDescription());
        updateReview.setDatetime(dto.getLocaldate());
        updateReview.setProduct(dto.getProduct());
        updateReview.setUsuario(dto.getUsuario());

        repository.save(updateReview);
    }

    public void deleteReview(Integer id){
        repository.deleteById(id);
    }

}
