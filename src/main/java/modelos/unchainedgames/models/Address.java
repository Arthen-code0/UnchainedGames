package modelos.unchainedgames.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "address", schema = "unchainedgames", catalog = "postgres")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "postal_code")
    private Integer postalCode;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @ManyToMany(mappedBy = "addresses")
    @ToString.Exclude
    private Set<Usuario> usuarios;
}