package modelos.unchainedgames.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import modelos.unchainedgames.models.Address;

import java.util.Set;

@Data
@Getter
@Setter
public class UsuarioCreateDTO {
    private String name;
    private String surnames;
    private String email;
    private String password;
    private String phoneNumber;
    private Set<Address> addresses;

}