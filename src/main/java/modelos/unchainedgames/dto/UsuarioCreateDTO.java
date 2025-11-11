package modelos.unchainedgames.dto;

import lombok.Data;
import modelos.unchainedgames.models.Address;

import java.util.Set;

@Data
public class UsuarioCreateDTO {
    private String name;
    private String surnames;
    private String phoneNumber;
    private String email;
    private String password;
    private Set<Address> addresses;
}