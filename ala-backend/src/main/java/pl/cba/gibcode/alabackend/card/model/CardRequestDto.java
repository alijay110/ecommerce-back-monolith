package pl.cba.gibcode.alabackend.card.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class CardRequestDto {

	private Long cardId;
	@NotNull
	private BigDecimal marketValue;
	@NotNull
	private BigDecimal price;
	@NotNull
	private CardTypeEnum cardType;
	@NotEmpty
	private String brandName;
	@NotEmpty
	private String cardNumber;

}
