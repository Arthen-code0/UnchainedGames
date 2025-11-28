// AuthResponse.java  
package modelos.unchainedgames.dto;

import lombok.Data;
import modelos.unchainedgames.models.Usuario;

@Data
public class AuthResponse {
    private String token;
    private Usuario usuario;
    private String message;

    public AuthResponse(String token, Usuario usuario, String message) {
        this.token = token;
        this.usuario = usuario;
        this.message = message;
    }
}