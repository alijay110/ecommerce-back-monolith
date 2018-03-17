package pl.cba.gibcode.alabackend.brand.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.cba.gibcode.alabackend.card.model.Card;
import pl.cba.gibcode.alabackend.card.model.CardType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    @ManyToMany(mappedBy = "brands", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<CardType> cardTypes = new HashSet<>();

    @ManyToMany(mappedBy = "brands", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(mappedBy = "brands", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<PriceRange> priceRanges = new HashSet<>();

    public void addCardType(CardType cardType){
        cardTypes.add(cardType);
        cardType.getBrands().add(this);
    }

    public void removeCardType(CardType cardType){
        cardTypes.remove(cardType);
        cardType.getBrands().remove(this);
    }

    public void addCategory(Category category){
        categories.add(category);
        category.getBrands().add(this);
    }

    public void removeCategory(Category category){
        categories.remove(category);
        category.getBrands().remove(this);
    }

    public void addPriceRange(PriceRange priceRange){
        priceRanges.add(priceRange);
        priceRange.getBrands().add(this);
    }

    public void removePriceRange(PriceRange priceRange){
        priceRanges.remove(priceRange);
        priceRange.getBrands().remove(this);
    }


    public void remove() {
        for(CardType cardType: new ArrayList<>(cardTypes)){
            removeCardType(cardType);
        }
        for(Category category: new ArrayList<>(categories)){
            removeCategory(category);
        }
        for(PriceRange priceRange: new ArrayList<>(priceRanges)){
            removePriceRange(priceRange);
        }
    }


}

