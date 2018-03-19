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

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final BrandRepository brandRepository;

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    @Transactional
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        log.info("Loading test data");
        Stream.generate(() -> createBrand(UUID.randomUUID().toString(), CardTypeEnum.PHYSICAL, "../../assets/img/no-image-gift-card.jpg"))
                .limit(30).collect(Collectors.toList());
        Stream.generate(() -> createBrand(UUID.randomUUID().toString(), CardTypeEnum.ELECTRONIC, "../../assets/img/sephora-gift-card.png"))
                .limit(300).collect(Collectors.toList());
    }

    private Brand createBrand(String name, CardTypeEnum cardType, String imgUrl) {
        Brand brand = new Brand();
        brand.setActive(Boolean.TRUE);
        brand.setMaxDiscount(BigDecimal.TEN);
        brand.setName(name);
        brand.addCardType(cardType);
        brand.setImgUrl(imgUrl);
        brand.addCategory(CategoryEnum.CULTURE);
        brand.addCategory(CategoryEnum.FASHION);
        return brandRepository.save(brand);
    }
}
