package modelos.unchainedgames.dto;

import lombok.Data;
import modelos.unchainedgames.listed.Rol;
import modelos.unchainedgames.models.Address;
import modelos.unchainedgames.models.Usuario;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UsuarioMostrarDTO {
    private Integer id;
    private String name;
    private String surnames;
    private String username;
    private String email;
    private String phoneNumber;
    private Rol rol;
    private Boolean enabled;
    private Set<AddressCreateDTO> addresses;

    // 🔹 Constructor vacío requerido por frameworks
    public UsuarioMostrarDTO() {}

    // 🔹 Constructor para mapear desde Usuario
    public UsuarioMostrarDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.name = usuario.getName();
        this.surnames = usuario.getSurnames();
        this.username = usuario.getUsername();
        this.email = usuario.getEmail();
        this.phoneNumber = usuario.getPhoneNumber();
        this.rol = usuario.getRol();
        this.enabled = usuario.getEnabled();

        // Si el usuario tiene direcciones, las convertimos al DTO
        if (usuario.getAddresses() != null) {
            this.addresses = usuario.getAddresses().stream()
                    .map(AddressCreateDTO::new)
                    .collect(Collectors.toSet());
        }
    }
}
