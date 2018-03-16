package pl.cba.gibcode.alabackend.card.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.cba.gibcode.alabackend.brand.model.Brand;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal price;
    @Column(nullable = false)
    private BigDecimal discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_type_id")
    private CardType cardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

}
