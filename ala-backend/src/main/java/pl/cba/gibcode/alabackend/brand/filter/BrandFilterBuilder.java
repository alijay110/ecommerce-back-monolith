package pl.cba.gibcode.alabackend.brand.filter;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.cba.gibcode.alabackend.brand.model.BrandCriteriaDto;
import pl.cba.gibcode.alabackend.brand.model.CategoryEnum;
import pl.cba.gibcode.alabackend.brand.model.PriceRangeEnum;
import pl.cba.gibcode.alabackend.brand.model.QBrand;
import pl.cba.gibcode.alabackend.card.model.CardTypeEnum;
import pl.cba.gibcode.alabackend.shared.WhereClauseBuilder;

import java.util.Optional;

import static java.util.Objects.nonNull;


@Component
@RequiredArgsConstructor
public class BrandFilterBuilder {

    private final static QBrand BRAND = QBrand.brand;

    public Predicate getBrandsFiltered(BrandCriteriaDto criteria) {
        Optional<CardTypeEnum> cardTypeOptional = Optional.empty();
        Optional<CategoryEnum> categoryOptional = Optional.empty();
        Optional<PriceRangeEnum> priceRangeOptional = Optional.empty();
        if (nonNull(criteria.getCardTypeId())) {
            cardTypeOptional = Optional.of(CardTypeEnum.fromOrdinal(criteria.getCardTypeId()));
        }
        if (nonNull(criteria.getCategoryId())) {
            categoryOptional = Optional.of(CategoryEnum.fromOrdinal(criteria.getCategoryId()));
        }
        if (nonNull(criteria.getPriceRangeId())) {
            priceRangeOptional = Optional.of(PriceRangeEnum.fromOrdinal(criteria.getPriceRangeId()));
        }

        return new WhereClauseBuilder(BRAND.id.isNotNull().and(BRAND.active.isTrue()))
                .optionalAnd(BRAND.name::startsWithIgnoreCase, criteria.getBrandLetter())
                .optionalAnd(BRAND.cardTypes::contains, cardTypeOptional.orElse(null))
                .optionalAnd(BRAND.categories::contains, categoryOptional.orElse(null))
                .optionalAnd(BRAND.priceRanges::contains, priceRangeOptional.orElse(null))
                .build();
    }
}
