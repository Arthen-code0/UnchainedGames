package modelos.unchainedgames.dto;

import jakarta.persistence.*;
import lombok.Data;
import modelos.unchainedgames.listed.Rol;
import modelos.unchainedgames.models.Address;

import java.util.Set;

@Data
public class UsuarioMostrarDTO {
    private Integer id;
    private String name;
    private String surnames;
    private String username;
    private String email;
    private String phoneNumber;
    private String profilePhoto;
    private Rol rol;
    private Boolean enabled;
    private Set<AddressCreateDTO> addresses;
}

