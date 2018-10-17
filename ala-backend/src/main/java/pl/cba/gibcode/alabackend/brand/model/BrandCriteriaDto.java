package pl.cba.gibcode.alabackend.brand.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.cba.gibcode.alabackend.card.model.CardTypeEnum;
import pl.cba.gibcode.alabackend.shared.model.PriceRangeEnum;

@NoArgsConstructor
@Getter
@Setter
public class BrandCriteriaDto {
    private String brandLetter;
    private PriceRangeEnum priceRangeId;
    private CategoryEnum categoryId;
    private CardTypeEnum cardTypeId;
}
