package modelos.unchainedgames.dto;

import lombok.*;
import modelos.unchainedgames.models.Usuario;

import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressCreateDTO {
    private Integer postalCode;
    private String street;
    private Integer number;
    private Integer floor;
    private String city;
    private String province;
    private Set<Usuario> usuarios;
}