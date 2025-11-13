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
@Table(name = "mechanics", schema = "UnchainedGames", catalog = "postgres")
public class Mechanics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    // Relación inversa opcional (no obligatoria)
    @ManyToMany(mappedBy = "mechanics")
    @ToString.Exclude
    private Set<Product> products;
}
