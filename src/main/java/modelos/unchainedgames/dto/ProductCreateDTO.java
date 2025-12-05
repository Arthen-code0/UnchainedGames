package modelos.unchainedgames.dto;

import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDTO {

    private String name;
    private String picture;
    private Integer playerMin;
    private Integer playerMax;
    private Double duration;
    private String recommendedAge;
    private Double price;
    private Integer stock;
    private String boxSize;
    private String difficulty;
    private String description;

    private Set<Integer> languagesIds;
}
