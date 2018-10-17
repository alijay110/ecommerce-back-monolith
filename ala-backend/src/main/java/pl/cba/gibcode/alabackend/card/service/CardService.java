package pl.cba.gibcode.alabackend.card.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cba.gibcode.alabackend.brand.model.Brand;
import pl.cba.gibcode.alabackend.brand.repository.BrandRepository;
import pl.cba.gibcode.alabackend.card.filter.CardFilterBuilder;
import pl.cba.gibcode.alabackend.card.mapper.CardMapper;
import pl.cba.gibcode.alabackend.card.model.*;
import pl.cba.gibcode.alabackend.card.repository.CardRepository;
import pl.cba.gibcode.alabackend.shared.BusinessException;
import pl.cba.gibcode.alabackend.shared.model.PriceRangeEnum;
import pl.cba.gibcode.alabackend.user.model.UserResponseDto;
import pl.cba.gibcode.alabackend.user.model.UserType;
import pl.cba.gibcode.alabackend.user.repository.UserRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class CardService {

	private final CardFilterBuilder cardFilterBuilder;
	private final CardRepository cardRepository;
	private final BrandRepository brandRepository;
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public Page<CardResponseDto> findAll(Pageable pageable, CardCriteriaDto criteria) {
		Predicate predicate = cardFilterBuilder.getCardsFiltered(criteria);
		return cardRepository.findAll(predicate, pageable).map(CardMapper::map);
	}

	@Transactional(readOnly = true)
	public Page<CardResponseDto> findAllForAdmin(Pageable pageable, CardCriteriaDto criteria) {
		Predicate predicate = cardFilterBuilder.getCardsFilteredForAdmin(criteria);
		return cardRepository.findAll(predicate, pageable).map(CardMapper::map);
	}

	@Transactional
	public CardResponseDto createCard(CardRequestDto cardRequestDto, UserResponseDto userDto) {
		Brand brand = brandRepository.findByName(cardRequestDto.getBrandName())
				.orElseThrow(() -> new BusinessException(String
						.format("Brand with name %s doesnt exist", cardRequestDto.getBrandName())));
		Card card = CardMapper.map(cardRequestDto, brand);
		card.setSeller(userRepository.findByUsername(userDto.getUsername()).orElseThrow(() -> new BusinessException("No user found for " + userDto.getUsername())));
		card.setDiscount(calculateDiscount(cardRequestDto.getMarketValue(), cardRequestDto.getPrice()));
		card.setPriceRangeEnum(PriceRangeEnum.decideRangeFrom(cardRequestDto.getMarketValue().doubleValue()));
		card.setCardNumber(cardRequestDto.getCardNumber());

		Card savedCard = cardRepository.save(card);
		brand.getCards().add(savedCard);
		brand.addPriceRange(savedCard.getPriceRangeEnum());
		brand.addCardType(savedCard.getCardType());

		updateMaxDiscountFor(brand);

		return CardMapper.map(card);
	}

	@Transactional
	public CardResponseDto updateCard(CardRequestDto cardRequestDto, UserResponseDto user) {
		Card card = cardRepository.findById(cardRequestDto.getCardId()).orElseThrow(() -> new BusinessException(String
				.format("No such card with id %s", cardRequestDto.getCardId())));
		if(!user.getUserRoles().contains(UserType.ADMIN) && !card.getSeller().getUsername().equals(user.getUsername())) {
			throw new BusinessException("User has no rights to update the card");
		}
		PriceRangeEnum oldPriceRangeEnum = card.getPriceRangeEnum();
		card.setCardNumber(cardRequestDto.getCardNumber());
		card.setPrice(cardRequestDto.getPrice());
		card.setMarketValue(cardRequestDto.getMarketValue());
		card.setDiscount(calculateDiscount(cardRequestDto.getMarketValue(), cardRequestDto.getPrice()));
		PriceRangeEnum priceRangeEnum = PriceRangeEnum.decideRangeFrom(cardRequestDto.getMarketValue().doubleValue());
		card.setPriceRangeEnum(priceRangeEnum);
		cardRepository.save(card);
		checkPriceRangeInBrandBecauseOf(card.getBrand(), oldPriceRangeEnum);
		updateMaxDiscountFor(card.getBrand());
		return CardMapper.map(card);
	}

	@Transactional
	public CardResponseDto deleteCard(Long cardId, UserResponseDto user) {
		Card card = cardRepository.findById(cardId).orElseThrow(() -> new BusinessException(String
				.format("No such card with id %s", cardId)));
		if(!user.getUserRoles().contains(UserType.ADMIN) && !card.getSeller().getUsername().equals(user.getUsername())) {
			throw new BusinessException("User has no rights to delete the card");
		}
		if(card.getSold()){
			throw new BusinessException("Card cannot be deleted when it was already sold to a seller");
		}
		card.setDeleted(true);
		checkPriceRangeInBrandBecauseOf(card.getBrand(), card.getPriceRangeEnum());
		updateMaxDiscountFor(card.getBrand());
		return CardMapper.map(card);
	}

	@Transactional
	public CardResponseDto validate(Long cardId, UserResponseDto user) {
		Card card = cardRepository.findById(cardId).orElseThrow(() -> new BusinessException(String
				.format("No such card with id %s", cardId)));
		if(!user.getUserRoles().contains(UserType.ADMIN)) {
			throw new BusinessException("User has no rights to validate the card");
		}
		card.setValidated(true);
		updateMaxDiscountFor(card.getBrand());
		return CardMapper.map(card);
	}

	@Transactional(readOnly = true)
	public Card checkCardAvailability(Long cardId){
		Card card = cardRepository.findById(cardId).orElseThrow(() -> new BusinessException(String
				.format("No such card with id %s", cardId)));
		if(nonNull(card.getBuyer()) || card.getSold()){
			throw new BusinessException(String.format("Card can't be sold. Card  %s already has been sold", card.getId()));
		}
		return card;
	}

	public void updateMaxDiscountFor(Brand brand) {
		Optional<Card> cardWithMaxDiscount = brand.getCards()
				.stream()
				.filter(Card::getValidated)
				.filter(card -> isNull(card.getBuyer()))
				.filter(card -> !card.getSold())
				.max(Comparator.comparing(Card::getDiscount));
		if(cardWithMaxDiscount.isPresent()){
			brand.setActive(true);
			brand.setMaxDiscount(cardWithMaxDiscount.get().getDiscount());
		}else{
			brand.setActive(false);
		}
	}

	private BigDecimal calculateDiscount(BigDecimal marketValue, BigDecimal price) {
		return BigDecimal.ONE.subtract(price.divide(marketValue, 8, RoundingMode.CEILING));
	}

	private void checkPriceRangeInBrandBecauseOf(Brand brand, PriceRangeEnum priceRangeEnum) {
		boolean hasMoreOldPriceRangeCards = brand.getCards().stream()
				.anyMatch(card -> card.getPriceRangeEnum().equals(priceRangeEnum));
		if(!hasMoreOldPriceRangeCards) {
			brand.removePriceRange(priceRangeEnum);
		}
	}


}
