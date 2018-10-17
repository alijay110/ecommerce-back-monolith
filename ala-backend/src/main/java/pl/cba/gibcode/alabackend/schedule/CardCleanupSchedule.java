package pl.cba.gibcode.alabackend.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.cba.gibcode.alabackend.card.service.CardService;
import pl.cba.gibcode.alabackend.transaction.model.TransactionStatus;
import pl.cba.gibcode.alabackend.transaction.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CardCleanupSchedule {

	private final TransactionRepository transactionRepository;
	private final CardService cardService;
	@Transactional
	@Scheduled(fixedDelay = 10000)
	public void cleanNotFinishedCheckoutsWithin30Minutes() {
		transactionRepository.findAll().stream().filter(transaction ->
				Objects.equals(transaction.getStatus(), TransactionStatus.PENDING) &&
						LocalDateTime.now().minusHours(2).isAfter(transaction.getDate())
		).forEach(transaction -> {
			transaction.getCard().setBuyer(null);
			transaction.setStatus(TransactionStatus.CANCELLED);
			cardService.updateMaxDiscountFor(transaction.getCard().getBrand());
		});
	}
}
