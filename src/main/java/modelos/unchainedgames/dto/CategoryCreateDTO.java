package modelos.unchainedgames.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import modelos.unchainedgames.models.Product;

import java.util.Set;

@Data
@Getter
@Setter
public class CategoryCreateDTO {
    private String name;
    private Set<Product> products;
}