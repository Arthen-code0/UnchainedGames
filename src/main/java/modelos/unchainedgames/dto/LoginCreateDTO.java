// LoginRequest.java
package modelos.unchainedgames.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginCreateDTO {
    private String email;
    private String password;
}