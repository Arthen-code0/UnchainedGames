package modelos.unchainedgames.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import modelos.unchainedgames.models.Usuario;

import java.util.Set;

@Data
@Getter
@Setter
public class AddressCreateDTO {
    private Integer postalCode;
    private String street;
    private Integer number;
    private Integer floor;
    private String city;
    private String province;
    private Set<Usuario> usuarios;
}