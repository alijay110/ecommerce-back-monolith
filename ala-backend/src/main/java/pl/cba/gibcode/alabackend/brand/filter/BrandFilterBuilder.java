package pl.cba.gibcode.alabackend.brand.filter;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.cba.gibcode.alabackend.brand.model.BrandCriteriaDto;
import pl.cba.gibcode.alabackend.brand.model.Category;
import pl.cba.gibcode.alabackend.brand.model.PriceRange;
import pl.cba.gibcode.alabackend.brand.model.QBrand;
import pl.cba.gibcode.alabackend.brand.repository.CategoryRepository;
import pl.cba.gibcode.alabackend.brand.repository.PriceRangeRepository;
import pl.cba.gibcode.alabackend.card.model.CardType;
import pl.cba.gibcode.alabackend.card.repository.CardTypeRepository;
import pl.cba.gibcode.alabackend.shared.WhereClauseBuilder;

import java.util.Optional;

import static java.util.Objects.nonNull;


@Component
@RequiredArgsConstructor
public class BrandFilterBuilder {

    private final static QBrand BRAND = QBrand.brand;

    private final CardTypeRepository cardTypeRepository;
    private final CategoryRepository categoryRepository;
    private final PriceRangeRepository priceRangeRepository;

    public Predicate getBrandsFiltered(BrandCriteriaDto criteria) {
        Optional<CardType> cardTypeOptional = Optional.empty();
        Optional<Category> categoryOptional = Optional.empty();
        Optional<PriceRange> priceRangeOptional = Optional.empty();
        if (nonNull(criteria.getCardTypeId())) {
            cardTypeOptional = cardTypeRepository.findOneById(criteria.getCardTypeId());
        }
        if (nonNull(criteria.getCategoryId())) {
            categoryOptional = categoryRepository.findOneById(criteria.getCategoryId());
        }
        if (nonNull(criteria.getPriceRangeId())) {
            priceRangeOptional = priceRangeRepository.findOneById(criteria.getPriceRangeId());
        }

        return new WhereClauseBuilder(BRAND.id.isNotNull().and(BRAND.active.isTrue()))
                .optionalAnd(BRAND.name::startsWithIgnoreCase, criteria.getBrandLetter())
                .optionalAnd(BRAND.cardTypes::contains, cardTypeOptional.orElse(null))
                .optionalAnd(BRAND.categories::contains, categoryOptional.orElse(null))
                .optionalAnd(BRAND.priceRanges::contains, priceRangeOptional.orElse(null))
                .build();
    }
}
