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
@Table(name = "language", schema = "UnchainedGames", catalog = "postgres")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    // Relación inversa opcional
    @ManyToMany(mappedBy = "languages")
    @ToString.Exclude
    private Set<Product> products;
}
