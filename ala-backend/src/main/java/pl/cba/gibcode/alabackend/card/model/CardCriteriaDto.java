package pl.cba.gibcode.alabackend.card.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import pl.cba.gibcode.alabackend.shared.model.PriceRangeEnum;

@NoArgsConstructor
@Getter
@Setter
public class CardCriteriaDto {
	private PriceRangeEnum priceRangeId;
	private String brandName;

}
