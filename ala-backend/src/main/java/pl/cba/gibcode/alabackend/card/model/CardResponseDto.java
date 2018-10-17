package pl.cba.gibcode.alabackend.card.model;

import lombok.Value;
import pl.cba.gibcode.alabackend.shared.model.PriceRangeEnum;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
public class CardResponseDto {
	private Long cardId;
	private BigDecimal price;
	private BigDecimal discount;
	private CardTypeEnum cardType;
	private PriceRangeEnum priceRangeEnum;
	private String brandName;
	private Boolean sold;
	private Boolean validated;
}
