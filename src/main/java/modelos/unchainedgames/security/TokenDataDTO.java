package modelos.unchainedgames.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelos.unchainedgames.listed.Rol;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDataDTO {

    private String MailUsername;
    private Rol rol;
    private String fechaCreacion;
    private String fechaExpiracion;

}
