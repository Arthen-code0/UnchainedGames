package modelos.unchainedgames.dto;

import lombok.Data;
import modelos.unchainedgames.models.Product;

import java.util.Set;

@Data
public class CategoryCreateDTO {
    private String name;
    private Set<Product> products;
}