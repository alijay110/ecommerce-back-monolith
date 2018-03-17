package pl.cba.gibcode.alabackend.card.model;

import lombok.*;
import pl.cba.gibcode.alabackend.brand.model.Brand;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(exclude = "brands")
@EqualsAndHashCode(exclude = "brands")
@NoArgsConstructor
public class CardType {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "CardType_Brand",
            joinColumns = {@JoinColumn(
                    name = "card_type_id",
                    referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(
                    name = "brand_id",
                    referencedColumnName = "id")}
    )
    private Set<Brand> brands = new HashSet<>();
}
