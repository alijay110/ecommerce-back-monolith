package pl.cba.gibcode.alabackend.brand.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.cba.gibcode.alabackend.card.model.Card;
import pl.cba.gibcode.alabackend.card.model.CardTypeEnum;
import pl.cba.gibcode.alabackend.shared.model.PriceRangeEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"cards"} )
@NoArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String imgUrl = "../../assets/img/no-image-gift-card.jpg";
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

    @ElementCollection(targetClass = CardTypeEnum.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "brand_cardtype")
    @Column(name = "card_type")
    private Set<CardTypeEnum> cardTypes = new HashSet<>();

    @ElementCollection(targetClass = CategoryEnum.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "brand_category")
    @Column(name = "category")
    private Set<CategoryEnum> categories = new HashSet<>();


    @ElementCollection(targetClass = PriceRangeEnum.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "brand_pricerange")
    @Column(name = "price_range")
    private Set<PriceRangeEnum> priceRanges = new HashSet<>();

    @Column(nullable = false)
    private Boolean deleted = Boolean.FALSE;


    public void addCardType(CardTypeEnum cardType) {
        cardTypes.add(cardType);
    }

    public void removeCardType(CardTypeEnum cardType) {
        cardTypes.remove(cardType);
    }

    public void addCategory(CategoryEnum category) {
        categories.add(category);
    }

    public void removeCategory(CategoryEnum category) {
        categories.remove(category);
    }

    public void addPriceRange(PriceRangeEnum priceRange) {
        priceRanges.add(priceRange);
    }

    public void removePriceRange(PriceRangeEnum priceRange) {
        priceRanges.remove(priceRange);
    }

}

