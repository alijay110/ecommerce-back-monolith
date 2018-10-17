package pl.cba.gibcode.alabackend.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.cba.gibcode.alabackend.card.model.CardResponseDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryDto {
	private String cardNumber;
	private Long transactionId;
	private TransactionStatus transactionStatus;
	private CardResponseDto cardResponseDto;
}
