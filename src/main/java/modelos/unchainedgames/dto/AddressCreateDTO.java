package modelos.unchainedgames.dto;

import lombok.Data;
import modelos.unchainedgames.models.Usuario;

import java.util.Set;

@Data
public class AddressCreateDTO {
    private Integer postalCode;
    private String street;
    private Integer number;
    private Integer floor;
    private String city;
    private String province;
    private Set<Usuario> usuarios;
}