package pl.cba.gibcode.alabackend.card.filter;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.cba.gibcode.alabackend.card.model.CardCriteriaDto;
import pl.cba.gibcode.alabackend.card.model.QCard;
import pl.cba.gibcode.alabackend.shared.WhereClauseBuilder;

@Component
@RequiredArgsConstructor
public class CardFilterBuilder {

	private final static QCard CARD = QCard.card;

	public Predicate getCardsFiltered(CardCriteriaDto criteria) {
		return new WhereClauseBuilder(CARD.id.isNotNull().and(CARD.validated.isTrue()
				.and(CARD.deleted.isFalse()))
				.and(CARD.sold.isFalse()))
				.optionalAnd(CARD.brand.name::eq, criteria.getBrandName())
				.optionalAnd(CARD.priceRangeEnum::eq, criteria.getPriceRangeId())
				.build();
	}

	public Predicate getCardsFilteredForAdmin(CardCriteriaDto criteria) {
		return new WhereClauseBuilder(CARD.id.isNotNull())
				.optionalAnd(CARD.brand.name::eq, criteria.getBrandName())
				.optionalAnd(CARD.priceRangeEnum::eq, criteria.getPriceRangeId())
				.build();
	}

}
