package pl.cba.gibcode.alabackend.brand.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class BrandCriteriaDto {
    private String brandLetter;
    private Long priceRangeId;
    private Long categoryId;
    private Long cardTypeId;
}
