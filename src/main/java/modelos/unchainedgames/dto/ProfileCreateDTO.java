package modelos.unchainedgames.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCreateDTO {
    private Integer id;
    private String name;
    private String surnames;
    private String fotoPerfilLink;
    private String email;
    private String phoneNumber;
    private String username;
}
