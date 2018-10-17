package pl.cba.gibcode.alabackend.card.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.cba.gibcode.alabackend.brand.model.Brand;
import pl.cba.gibcode.alabackend.shared.model.PriceRangeEnum;
import pl.cba.gibcode.alabackend.user.model.User;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(exclude = {"brand", "buyer", "seller"})
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal marketValue;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private BigDecimal discount;
    @Column(nullable = false)
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    private CardTypeEnum cardType;

    @Column(nullable = false)
    private Boolean validated = Boolean.FALSE;

    @Column(nullable = false)
    private Boolean sold = Boolean.FALSE;

    @Column(nullable = false)
    private Boolean deleted = Boolean.FALSE;

    @Column(nullable = false)
    private PriceRangeEnum priceRangeEnum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;
}
