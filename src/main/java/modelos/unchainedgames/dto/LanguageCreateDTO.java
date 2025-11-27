package modelos.unchainedgames.dto;

import lombok.*;
import modelos.unchainedgames.models.Product;

import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LanguageCreateDTO {
    private String name;
    private Set<Product> products;
}