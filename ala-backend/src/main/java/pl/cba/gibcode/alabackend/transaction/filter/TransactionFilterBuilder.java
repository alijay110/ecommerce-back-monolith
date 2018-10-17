package pl.cba.gibcode.alabackend.transaction.filter;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.cba.gibcode.alabackend.brand.model.BrandCriteriaDto;
import pl.cba.gibcode.alabackend.brand.model.QBrand;
import pl.cba.gibcode.alabackend.shared.WhereClauseBuilder;
import pl.cba.gibcode.alabackend.transaction.model.QTransaction;

@Component
@RequiredArgsConstructor
public class TransactionFilterBuilder {


	private final static QTransaction TRANSACTION = QTransaction.transaction;

	public Predicate filterForBuyer(String username) {
		return new WhereClauseBuilder(TRANSACTION.id.isNotNull().and(TRANSACTION.card.buyer.username.eq(username))).build();
	}

	public Predicate filterForSeller(String username) {
		return new WhereClauseBuilder(TRANSACTION.id.isNotNull().and(TRANSACTION.card.seller.username.eq(username))).build();
	}
}