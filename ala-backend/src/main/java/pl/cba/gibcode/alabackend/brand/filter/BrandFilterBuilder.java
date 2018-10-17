package pl.cba.gibcode.alabackend.brand.filter;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.cba.gibcode.alabackend.brand.model.BrandCriteriaDto;
import pl.cba.gibcode.alabackend.brand.model.CategoryEnum;
import pl.cba.gibcode.alabackend.shared.model.PriceRangeEnum;
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
        return new WhereClauseBuilder(BRAND.id.isNotNull().and(BRAND.active.isTrue())
                .and(BRAND.deleted.isFalse()))
                .optionalAnd(BRAND.name::startsWithIgnoreCase, criteria.getBrandLetter())
                .optionalAnd(BRAND.cardTypes::contains, criteria.getCardTypeId())
                .optionalAnd(BRAND.categories::contains, criteria.getCategoryId())
                .optionalAnd(BRAND.priceRanges::contains, criteria.getPriceRangeId())
                .build();
    }

    public Predicate getBrandsFilteredForAdmin(BrandCriteriaDto criteria) {
        return new WhereClauseBuilder(BRAND.id.isNotNull().and(BRAND.deleted.isFalse()))
                .optionalAnd(BRAND.name::startsWithIgnoreCase, criteria.getBrandLetter())
                .optionalAnd(BRAND.cardTypes::contains, criteria.getCardTypeId())
                .optionalAnd(BRAND.categories::contains, criteria.getCategoryId())
                .optionalAnd(BRAND.priceRanges::contains, criteria.getPriceRangeId())
                .build();
    }
}
