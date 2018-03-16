package pl.cba.gibcode.alabackend.brand.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.cba.gibcode.alabackend.card.model.Card;
import pl.cba.gibcode.alabackend.card.model.CardType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Brand {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String imgUrl = "../../assets/img/no-image-gift-card.png";
    @Column(nullable = false)
    private BigDecimal maxDiscount = BigDecimal.ZERO;
    @Column(nullable = false)
    private Boolean active = Boolean.FALSE;

    @OneToMany(
            mappedBy = "brand",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Card> cards = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "Brand_CardType",
            joinColumns = {@JoinColumn(name = "brand_id")},
            inverseJoinColumns = {@JoinColumn(name = "card_type_id")}
    )
    private Set<CardType> cardTypes = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Brand_Category",
            joinColumns = {@JoinColumn(name = "brand_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private Set<Category> categories = new HashSet<>();


    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Brand_PriceRange",
            joinColumns = {@JoinColumn(name = "brand_id")},
            inverseJoinColumns = {@JoinColumn(name = "price_range_id")}
    )
    private Set<PriceRange> priceRanges = new HashSet<>();

}

