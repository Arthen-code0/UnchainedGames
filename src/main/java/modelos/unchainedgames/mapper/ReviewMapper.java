package modelos.unchainedgames.mapper;

import modelos.unchainedgames.dto.ReviewMostrarDTO;
import modelos.unchainedgames.models.Product;
import modelos.unchainedgames.models.Review;
import modelos.unchainedgames.models.Usuario;

public class ReviewMapper {

    public static ReviewMostrarDTO toMostrarDTO(Review review) {
        if (review == null) return null;

        ReviewMostrarDTO dto = new ReviewMostrarDTO();
        dto.setId(review.getId());
        dto.setScore(review.getScore());
        dto.setDescription(review.getDescription());
        dto.setDatetime(review.getDatetime());

        Usuario u = review.getUsuario();
        if (u != null) {
            dto.setUsuarioId(u.getId());
            dto.setUsuarioName(u.getName() + " " + u.getSurnames());
        }

        Product p = review.getProduct();
        if (p != null) {
            dto.setProductId(p.getId());
            dto.setProductName(p.getName());
            dto.setProductPicture(p.getPicture());
        }

        return dto;
    }
}
