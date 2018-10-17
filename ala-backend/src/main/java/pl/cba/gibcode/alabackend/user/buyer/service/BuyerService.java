package pl.cba.gibcode.alabackend.user.buyer.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cba.gibcode.alabackend.card.model.Card;
import pl.cba.gibcode.alabackend.card.service.CardService;
import pl.cba.gibcode.alabackend.shared.BusinessException;
import pl.cba.gibcode.alabackend.transaction.filter.TransactionFilterBuilder;
import pl.cba.gibcode.alabackend.transaction.mapper.SummaryMapper;
import pl.cba.gibcode.alabackend.transaction.model.SummaryDto;
import pl.cba.gibcode.alabackend.transaction.model.Transaction;
import pl.cba.gibcode.alabackend.transaction.model.TransactionStatus;
import pl.cba.gibcode.alabackend.transaction.repository.TransactionRepository;
import pl.cba.gibcode.alabackend.user.model.User;
import pl.cba.gibcode.alabackend.user.model.UserResponseDto;
import pl.cba.gibcode.alabackend.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class BuyerService {

	private final CardService cardService;
	private final TransactionRepository transactionRepository;
	private final UserRepository userRepository;
	private final TransactionFilterBuilder transactionFilterBuilder;

	@Transactional
	public SummaryDto checkout(Long cardId, UserResponseDto login) {
		User user = userRepository.findByUsername(login.getUsername())
				.orElseThrow(() -> new BusinessException("No such username found"));
		Card card = cardService.checkCardAvailability(cardId);
		card.setBuyer(user);
		Transaction transaction = new Transaction();
		transaction.setCard(card);
		cardService.updateMaxDiscountFor(card.getBrand());
		Transaction savedTransaction = transactionRepository.save(transaction);
		return SummaryMapper.mapWithoutCardNumber(savedTransaction);
	}

	@Transactional
	public SummaryDto pay(Long transactionId, UserResponseDto login) {
		User user = userRepository.findByUsername(login.getUsername())
				.orElseThrow(() -> new BusinessException("No such username found"));
		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new BusinessException(String.format("No such transaction %s found",
						transactionId)));
		if(!transaction.getCard().getBuyer().getUsername().equals(user.getUsername())) {
			transaction.setStatus(TransactionStatus.PENDING);
			transactionRepository.save(transaction);
			throw new BusinessException(String
					.format("User %s is not a buyer for transaction %s", user.getUsername(), transaction.getId()));
		}
		transaction.setStatus(TransactionStatus.PAID);
		transaction.getCard().setSold(true);
		return SummaryMapper.mapWithCardNumber(transaction);
	}

	@Transactional(readOnly = true)
	public Page<SummaryDto> findAllTransactionsForBuyer(Pageable pageable, UserResponseDto login) {
		Predicate predicate = transactionFilterBuilder.filterForBuyer(login.getUsername());
		return transactionRepository.findAll(predicate, pageable)
				.map(
						transaction -> {
							if(transaction.getStatus().equals(TransactionStatus.PAID)) {
								return SummaryMapper.mapWithCardNumber(transaction);
							} else {
								return SummaryMapper.mapWithoutCardNumber(transaction);
							}
						}
				);
	}
}
