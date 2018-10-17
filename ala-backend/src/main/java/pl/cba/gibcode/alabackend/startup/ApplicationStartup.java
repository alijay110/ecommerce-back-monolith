package pl.cba.gibcode.alabackend.startup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.cba.gibcode.alabackend.brand.model.Brand;
import pl.cba.gibcode.alabackend.brand.model.CategoryEnum;
import pl.cba.gibcode.alabackend.brand.repository.BrandRepository;
import pl.cba.gibcode.alabackend.card.model.CardTypeEnum;
import pl.cba.gibcode.alabackend.shared.model.PriceRangeEnum;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private static final String IMG_BASE_URL = "../../assets/img/";
    private final BrandRepository brandRepository;

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    @Transactional
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        log.info("Loading test data");
        brandRepository.deleteAll();
        brandRepository.flush();
        createBrand("Adidas", IMG_BASE_URL + "adidas-gc-taxon.1.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC, CardTypeEnum.PHYSICAL),
                Collections.singletonList(CategoryEnum.FASHION),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("Airbnb", IMG_BASE_URL + "airbnb-taxon.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC, CardTypeEnum.PHYSICAL),
                Collections.singletonList(CategoryEnum.SERVICES),
                Arrays.asList(PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("Aldo", IMG_BASE_URL + "Aldo.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC, CardTypeEnum.PHYSICAL),
                Collections.singletonList(CategoryEnum.FASHION),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY));
        createBrand("BP", IMG_BASE_URL + "bp_taxon.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC),
                Collections.singletonList(CategoryEnum.SERVICES),
                Arrays.asList(PriceRangeEnum.FIFTY_HUNDRED, PriceRangeEnum.HUNDRED_FIVEHUNDRED));
        createBrand("Burger King", IMG_BASE_URL + "burger-king-gift-card.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC, CardTypeEnum.PHYSICAL),
                Collections.singletonList(CategoryEnum.RESTAURANTS),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("Circle", IMG_BASE_URL + "circle-k-gift-card.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC, CardTypeEnum.PHYSICAL),
                Collections.singletonList(CategoryEnum.SERVICES),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("Google Play", IMG_BASE_URL + "google-play-digital.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC, CardTypeEnum.PHYSICAL),
                Arrays.asList(CategoryEnum.ENTERTAINMENT, CategoryEnum.GAMES),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("H&M", IMG_BASE_URL + "h&m-gift-card.png",
                Arrays.asList(CardTypeEnum.PHYSICAL),
                Collections.singletonList(CategoryEnum.FASHION),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("IKEA", IMG_BASE_URL + "ikea-gift-card.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC, CardTypeEnum.PHYSICAL),
                Arrays.asList(CategoryEnum.HOUSEHOLD, CategoryEnum.ACCESSORIES),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("iTunes", IMG_BASE_URL + "itunes-gift-card.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC),
                Arrays.asList(CategoryEnum.GAMES, CategoryEnum.ENTERTAINMENT),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY));
        createBrand("Levi's", IMG_BASE_URL + "levis-gift-card.png",
                Arrays.asList(CardTypeEnum.PHYSICAL),
                Collections.singletonList(CategoryEnum.FASHION),
                Arrays.asList(PriceRangeEnum.HUNDRED_FIVEHUNDRED, PriceRangeEnum.FIVEHUNDRED_MORE));
        createBrand("Mc Donald's", IMG_BASE_URL + "mcdonald-s-gift-card.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC, CardTypeEnum.PHYSICAL),
                Collections.singletonList(CategoryEnum.RESTAURANTS),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("Michael Kors", IMG_BASE_URL + "michael-kors-gift-card.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC, CardTypeEnum.PHYSICAL),
                Collections.singletonList(CategoryEnum.FASHION),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("Nike", IMG_BASE_URL + "nike.jpg",
                Arrays.asList(CardTypeEnum.ELECTRONIC, CardTypeEnum.PHYSICAL),
                Arrays.asList(CategoryEnum.FASHION, CategoryEnum.SPORT),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("Pizza Hut", IMG_BASE_URL + "pizza-hut-block-letters-gift-card-taxon.png",
                Arrays.asList(CardTypeEnum.PHYSICAL),
                Collections.singletonList(CategoryEnum.RESTAURANTS),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("Ray Ban", IMG_BASE_URL + "ray-ban-gift-card.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC, CardTypeEnum.PHYSICAL),
                Collections.singletonList(CategoryEnum.FASHION),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("Reebok", IMG_BASE_URL + "reebok-gift-card-taxon.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC, CardTypeEnum.PHYSICAL),
                Arrays.asList(CategoryEnum.FASHION, CategoryEnum.SPORT),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("Sephora", IMG_BASE_URL + "sephora-gift-card.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC, CardTypeEnum.PHYSICAL),
                Collections.singletonList(CategoryEnum.HEALTH),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("Starbucks", IMG_BASE_URL + "starbucks-gift-card.png",
                Arrays.asList(CardTypeEnum.PHYSICAL),
                Collections.singletonList(CategoryEnum.RESTAURANTS),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("Steam", IMG_BASE_URL + "steam-gift-cards.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC),
                Arrays.asList(CategoryEnum.GAMES, CategoryEnum.ENTERTAINMENT),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("Subway", IMG_BASE_URL + "subway.jpg",
                Arrays.asList(CardTypeEnum.PHYSICAL),
                Collections.singletonList(CategoryEnum.RESTAURANTS),
                Arrays.asList(PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("Uber", IMG_BASE_URL + "uber-gift-card.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC, CardTypeEnum.PHYSICAL),
                Arrays.asList(CategoryEnum.SERVICES),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("Uber Eats", IMG_BASE_URL + "UE-gift-card-taxon.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC),
                Arrays.asList(CategoryEnum.SERVICES, CategoryEnum.RESTAURANTS),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("Victoria's Secret", IMG_BASE_URL + "victoriassecret.jpg",
                Arrays.asList(CardTypeEnum.PHYSICAL),
                Collections.singletonList(CategoryEnum.FASHION),
                Arrays.asList(PriceRangeEnum.FIFTY_HUNDRED));
        createBrand("X-Box", IMG_BASE_URL + "xbox-gift-card.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC, CardTypeEnum.PHYSICAL),
                Arrays.asList(CategoryEnum.SERVICES, CategoryEnum.ENTERTAINMENT, CategoryEnum.GAMES),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.HUNDRED_FIVEHUNDRED));
        createBrand("Zara", IMG_BASE_URL + "zara-gift-card-taxon.png",
                Arrays.asList(CardTypeEnum.ELECTRONIC, CardTypeEnum.PHYSICAL),
                Collections.singletonList(CategoryEnum.FASHION),
                Arrays.asList(PriceRangeEnum.ZERO_FIFTY, PriceRangeEnum.FIFTY_HUNDRED));
        log.info("Finished loading test data");
    }

    private Brand createBrand(String name, String imgUrl, List<CardTypeEnum> cardTypes, List<CategoryEnum> categories, List<PriceRangeEnum> priceRanges) {
        Brand brand = new Brand();
        brand.setActive(Boolean.TRUE);
        brand.setMaxDiscount(new BigDecimal(BigInteger.valueOf(new Random().nextInt(1000)), 2));
        brand.setName(name);
        brand.setImgUrl(imgUrl);
        cardTypes.forEach(brand::addCardType);
        categories.forEach(brand::addCategory);
        priceRanges.forEach(brand::addPriceRange);
        return brandRepository.save(brand);
    }
}
