package modelos.unchainedgames.models;

import jakarta.persistence.*;
import lombok.*;
import modelos.unchainedgames.listed.Rol;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "usuario", schema = "unchainedgames", catalog = "postgres")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "surnames")
    private String surnames;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    // ✅ AÑADE ESTE CAMPO:
    @Column(name = "username")
    private String username;

    @Column(name = "rol", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Rol rol = Rol.USUARIO;

    @Column(name = "enabled")
    private Boolean enabled = false;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "verification_code_expires_at")
    private LocalDateTime verificationCodeExpiresAt;

    @Column(name = "recovery_code")
    private String recoveryCode;

    @Column(name = "recovery_code_expires_at")
    private LocalDateTime recoveryCodeExpiresAt;

    @ManyToMany
    @JoinTable(
            name = "user_address",
            schema = "unchainedgames",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private Set<Address> addresses;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(List.of(new SimpleGrantedAuthority(this.rol.name()))); //Se crea una coleccion para meter dentro los roles de usuario
    }

    @Override
    public String getUsername() {
        return this.email;
    }


}