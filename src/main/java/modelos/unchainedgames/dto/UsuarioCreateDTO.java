package modelos.unchainedgames.dto;

import lombok.*;
import modelos.unchainedgames.models.Address;

import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class    UsuarioCreateDTO {
    private String name;
    private String surnames;
    private String email;
    private String password;
    private String phoneNumber;
    private Set<Address> addresses;

}