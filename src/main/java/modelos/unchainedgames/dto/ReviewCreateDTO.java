package modelos.unchainedgames.dto;

import lombok.Data;
import modelos.unchainedgames.models.Product;
import modelos.unchainedgames.models.Review;
import modelos.unchainedgames.models.Usuario;

import java.time.LocalDate;

@Data
public class ReviewCreateDTO {
    private Integer score;
    private String description;
    private LocalDate localdate;
    private Usuario usuario;
    private Product product;
}
