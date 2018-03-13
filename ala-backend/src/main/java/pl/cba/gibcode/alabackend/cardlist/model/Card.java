package pl.cba.gibcode.alabackend.cardlist.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Card {

    private String brand;
    private BigDecimal maxDiscount;
    private String imgUrl;

    public static Card buildRandomCard(){
        return new Card(UUID.randomUUID().toString(),BigDecimal.TEN, "../../assets/img/sephora-gift-card.png");
    }

}
