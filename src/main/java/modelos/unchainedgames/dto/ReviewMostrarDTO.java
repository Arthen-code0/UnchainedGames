package modelos.unchainedgames.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewMostrarDTO {

    private Integer id;
    private Integer score;
    private String description;
    private LocalDate datetime;

    private Integer usuarioId;
    private String usuarioName;

    private Integer productId;
    private String productName;
    private String productPicture;
}
