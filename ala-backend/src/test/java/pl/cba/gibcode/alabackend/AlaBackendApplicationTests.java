package pl.cba.gibcode.alabackend;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import pl.cba.gibcode.alabackend.brand.model.Brand;
import pl.cba.gibcode.alabackend.brand.repository.BrandRepository;
import pl.cba.gibcode.alabackend.card.model.Card;
import pl.cba.gibcode.alabackend.card.model.CardType;
import pl.cba.gibcode.alabackend.card.repository.CardTypeRepository;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional
public class AlaBackendApplicationTests {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CardTypeRepository cardTypeRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        brandRepository.deleteAll();
        cardTypeRepository.deleteAll();

    }

    @Test
    public void shouldReturnFilteredListOfBrands() throws Exception {
        //given
        CardType cardType = createCardType("ctype1");
        createBrand("atestX", cardType);
        createBrand("btestX", cardType);
        //expect
        log.info("logging: {}", mockMvc.perform(get("/api/brands?page=0&size=16&sort=name")
                .content("{\"brandLetter\":\"b\", " +
                        "\"cardTypeId\" :  " + cardType.getId() + "}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString());
    }

    public CardType createCardType(String name) {
        CardType cardType = new CardType();
        cardType.setName(name);
        return cardTypeRepository.saveAndFlush(cardType);
    }

    private Brand createBrand(String name, CardType savedCardType) {
        Brand brand = new Brand();
        brand.setActive(Boolean.TRUE);
        brand.setMaxDiscount(BigDecimal.TEN);
        brand.setName(name);
        brand.addCardType(savedCardType);
        brand.setImgUrl("test");
        return brandRepository.save(brand);
    }
}
