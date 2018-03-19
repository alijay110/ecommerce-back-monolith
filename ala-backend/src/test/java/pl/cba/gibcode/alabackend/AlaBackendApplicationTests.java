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
import pl.cba.gibcode.alabackend.card.model.CardTypeEnum;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional
public class AlaBackendApplicationTests {

    @Autowired
    private BrandRepository brandRepository;

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
    }

    @Test
    public void shouldReturnFilteredListOfBrands() throws Exception {
        //given
        //CardType cardType = createCardType("ctype1");
        createBrand("atestX", CardTypeEnum.ELECTRONIC);
        createBrand("btestX", CardTypeEnum.ELECTRONIC);
        //expect
        log.info("logging: {}", mockMvc.perform(get("/api/brands?page=0&size=16&sort=name")
                .content("{\"brandLetter\":\"b\", " +
                        "\"cardTypeId\" :  " + CardTypeEnum.ELECTRONIC.ordinal() + "}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString());
    }


    private Brand createBrand(String name, CardTypeEnum cardTypeEnum) {
        Brand brand = new Brand();
        brand.setActive(Boolean.TRUE);
        brand.setMaxDiscount(BigDecimal.TEN);
        brand.setName(name);
        brand.addCardType(cardTypeEnum);
        brand.setImgUrl("test");
        return brandRepository.save(brand);
    }
}
