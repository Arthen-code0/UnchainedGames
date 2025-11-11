package modelos.unchainedgames.dto;

import lombok.Data;
import modelos.unchainedgames.models.Product;

import java.util.Set;

@Data
public class MechanicsCreateDTO {
    private String name;
    private Set<Product> products;
}