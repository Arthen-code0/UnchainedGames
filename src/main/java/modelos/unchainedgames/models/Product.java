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
@Table(name = "product", schema = "unchainedgames", catalog = "postgres")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name  = "name")
    private String name;

    @Column(name = "player_min")
    private Integer playerMin;

    @Column(name = "player_max")
    private Integer playerMax;

    @Column(name = "duration")
    private Double duration;

    @Column(name = "recommended_age")
    private String recommendedAge;

    @Column(name = "price")
    private Double price;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "box_size")
    private String boxSize;

    @Column(name = "difficulty")
    private String difficulty;

    @Column(name = "description")
    private String description;

    // --- Relaciones ---

    @ManyToMany
    @JoinTable(
            name = "product_mechanics",
            schema = "unchainedgames",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "mechanics_id")
    )
    private Set<Mechanics> mechanics;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            schema = "unchainedgames",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @ManyToMany
    @JoinTable(
            name = "product_language",
            schema = "unchainedgames",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private Set<Language> languages;
}
