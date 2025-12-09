package modelos.unchainedgames.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateDTO {

    private Integer productId;
    private Integer score;
    private String description;
}
