package pl.cba.gibcode.alabackend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import pl.cba.gibcode.alabackend.brand.model.Brand;
import pl.cba.gibcode.alabackend.brand.model.CategoryEnum;
import pl.cba.gibcode.alabackend.brand.model.PriceRangeEnum;
import pl.cba.gibcode.alabackend.brand.repository.BrandRepository;
import pl.cba.gibcode.alabackend.card.model.CardTypeEnum;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Transactional
public class BrandFixture {
    private static final String DEFAULT_IMG_URL = "default/img/url";
    private final BrandRepository brandRepository;

    public void clearData(){
        brandRepository.deleteAll();
    }

    public Brand sephora() {
        return brandRepository.findByName("sephora")
                .orElseGet(() -> {
                    Brand brand = createBrand("sephora",
                            Arrays.asList(CardTypeEnum.ELECTRONIC, CardTypeEnum.PHYSICAL),
                            Collections.singletonList(CategoryEnum.HEALTH),
                            Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
                    return brandRepository.save(brand);
                });
    }

    public Brand zara() {
        return brandRepository.findByName("zara")
                .orElseGet(() -> {
                    Brand brand = createBrand("zara",
                            Arrays.asList(CardTypeEnum.ELECTRONIC, CardTypeEnum.PHYSICAL),
                            Collections.singletonList(CategoryEnum.FASHION),
                            Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
                    return brandRepository.save(brand);
                });
    }

    public Brand pizzaHut() {
        return brandRepository.findByName("pizzaHut")
                .orElseGet(() -> {
                    Brand brand = createBrand("pizzaHut",
                            Collections.singletonList(CardTypeEnum.PHYSICAL),
                            Collections.singletonList(CategoryEnum.RESTAURANTS),
                            Collections.singletonList(PriceRangeEnum.FIFTY_HUNDRED));
                    return brandRepository.save(brand);
                });
    }

    public Brand subway() {
        return brandRepository.findByName("subway")
                .orElseGet(() -> {
                    Brand brand = createBrand("subway",
                            Collections.singletonList(CardTypeEnum.ELECTRONIC),
                            Collections.singletonList(CategoryEnum.RESTAURANTS),
                            Collections.singletonList(PriceRangeEnum.HUNDRED_FIVEHUNDRED));
                    return brandRepository.save(brand);
                });
    }

    public Brand carrefour() {
        return brandRepository.findByName("carrefour")
                .orElseGet(() -> {
                    Brand brand = createBrand("carrefour",
                            Collections.singletonList(CardTypeEnum.PHYSICAL),
                            Collections.singletonList(CategoryEnum.GROCERIES),
                            Collections.singletonList(PriceRangeEnum.HUNDRED_FIVEHUNDRED));
                    return brandRepository.save(brand);
                });
    }

    private Brand createBrand(String name, List<CardTypeEnum> cardTypes, List<CategoryEnum> categories, List<PriceRangeEnum> priceRanges) {
        Brand brand = new Brand();
        brand.setActive(Boolean.TRUE);
        brand.setMaxDiscount(new BigDecimal(BigInteger.valueOf(new Random().nextInt(1000)), 2));
        brand.setName(name);
        brand.setImgUrl(DEFAULT_IMG_URL);
        cardTypes.forEach(brand::addCardType);
        categories.forEach(brand::addCategory);
        priceRanges.forEach(brand::addPriceRange);
        return brand;
    }
}
