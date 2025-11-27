package modelos.unchainedgames.dto;

import lombok.*;
import modelos.unchainedgames.models.Category;
import modelos.unchainedgames.models.Language;
import modelos.unchainedgames.models.Mechanics;

import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDTO {

    private String name;
    private Integer playerMin;
    private Integer playerMax;
    private Double duration;
    private String recommendedAge;
    private Double price;
    private Integer stock;
    private String boxSize;
    private String difficulty;
    private String description;
    private Set<Mechanics> mechanics;
    private Set<Category> categories;
    private Set<Language> languages;

}
