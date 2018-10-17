package pl.cba.gibcode.alabackend.user.seller.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cba.gibcode.alabackend.card.model.CardTypeEnum;
import pl.cba.gibcode.alabackend.shared.BusinessException;
import pl.cba.gibcode.alabackend.transaction.filter.TransactionFilterBuilder;
import pl.cba.gibcode.alabackend.transaction.mapper.SummaryMapper;
import pl.cba.gibcode.alabackend.transaction.model.SummaryDto;
import pl.cba.gibcode.alabackend.transaction.model.Transaction;
import pl.cba.gibcode.alabackend.transaction.model.TransactionStatus;
import pl.cba.gibcode.alabackend.transaction.repository.TransactionRepository;
import pl.cba.gibcode.alabackend.user.model.User;
import pl.cba.gibcode.alabackend.user.model.UserDetails;
import pl.cba.gibcode.alabackend.user.model.UserDetailsDto;
import pl.cba.gibcode.alabackend.user.model.UserResponseDto;
import pl.cba.gibcode.alabackend.user.repository.UserDetailsRepository;
import pl.cba.gibcode.alabackend.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class SellerService {

	private final TransactionRepository transactionRepository;
	private final UserRepository userRepository;
	private final TransactionFilterBuilder transactionFilterBuilder;
	private final UserDetailsRepository userDetailsRepository;

	@Transactional
	public UserDetailsDto sendCard(Long transactionId, UserResponseDto login){
		User user = userRepository.findByUsername(login.getUsername()).orElseThrow( () -> new BusinessException("No such username found"));
		Transaction transaction = transactionRepository.findById(transactionId).orElseThrow( () -> new BusinessException(String.format("No such transaction %s found", transactionId)));
		if(!transaction.getStatus().equals(TransactionStatus.PAID)){
			throw new BusinessException(String.format("Card is not ready to be sent for transaction %s", transaction.getId()));
		}

		if(!transaction.getCard().getSeller().getUsername().equals(user.getUsername())){
			throw new BusinessException(String.format("User %s can't send the card %s which he is not an owner", user.getUsername(), transaction.getCard().getId()));
		}

		if(transaction.getCard().getCardType().equals(CardTypeEnum.ELECTRONIC)){
			throw new BusinessException(String.format("Card in transaction %s is electronic. No way to be sent!", transaction.getId()));
		}
		//get user details and return it
		UserDetails userDetails = userDetailsRepository.findByUser(user).orElseThrow(() -> new BusinessException("Counldnt find user details"));
		transaction.setStatus(TransactionStatus.SENT);
		return UserDetailsDto.of(user.getUsername(), userDetails.getFullName(), userDetails.getAddress());
	}

	@Transactional(readOnly = true)
	public Page<SummaryDto> findAllTransactionsForSeller(Pageable pageable, UserResponseDto login) {
		Predicate predicate = transactionFilterBuilder.filterForSeller(login.getUsername());
		return transactionRepository.findAll(predicate, pageable)
				.map(SummaryMapper::mapWithCardNumber);
	}
}
