package pl.cba.gibcode.alabackend.card.mapper;

import pl.cba.gibcode.alabackend.brand.model.Brand;
import pl.cba.gibcode.alabackend.card.model.Card;
import pl.cba.gibcode.alabackend.card.model.CardRequestDto;
import pl.cba.gibcode.alabackend.card.model.CardResponseDto;

public class CardMapper {

	public static CardResponseDto map(Card card) {
		return CardResponseDto.of(
				card.getId(),
				card.getPrice(),
				card.getDiscount(),
				card.getCardType(),
				card.getPriceRangeEnum(),
				card.getBrand().getName(),
				card.getSold(),
				card.getValidated());
	}

	public static Card map(CardRequestDto cardRequestDto, Brand brand){
		Card card = new Card();
		card.setBrand(brand);
		card.setMarketValue(cardRequestDto.getMarketValue());
		card.setPrice(cardRequestDto.getPrice());
		card.setCardType(cardRequestDto.getCardType());
		return card;
	}
}
