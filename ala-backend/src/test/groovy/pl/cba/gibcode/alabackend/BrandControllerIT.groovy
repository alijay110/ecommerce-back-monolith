package pl.cba.gibcode.alabackend

import net.minidev.json.JSONArray
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import pl.cba.gibcode.alabackend.brand.web.BrandController
import spock.lang.Specification

import static org.hamcrest.Matchers.isA
import static org.hamcrest.Matchers.iterableWithSize
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static pl.cba.gibcode.alabackend.brand.model.CategoryEnum.CULTURE
import static pl.cba.gibcode.alabackend.brand.model.CategoryEnum.RESTAURANTS
import static pl.cba.gibcode.alabackend.brand.model.PriceRangeEnum.FIFTY_HUNDRED
import static pl.cba.gibcode.alabackend.brand.model.PriceRangeEnum.ZERO_FIFTY
import static pl.cba.gibcode.alabackend.card.model.CardTypeEnum.ELECTRONIC
import static pl.cba.gibcode.alabackend.card.model.CardTypeEnum.PHYSICAL

@SpringBootTest(classes = AlaBackendApplication.class)
@AutoConfigureMockMvc
class BrandControllerIT extends Specification {

    def BRANDS_URL = "/api/brands"

    @Autowired
    BrandController brandController

    @Autowired
    MockMvc mvc

    @Autowired
    BrandFixture brandFixture

    def 'should return correct results when filtering brands'() {
        given:
        brandFixture.clearData()
        brandFixture.carrefour()
        brandFixture.pizzaHut()
        brandFixture.sephora()
        brandFixture.subway()
        brandFixture.zara()

        when:
        ResultActions response = mvc.perform(post(BRANDS_URL + params)
                .content(criteria)
                .contentType(APPLICATION_JSON))

        then:
        response.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath('$.content', isA(JSONArray.class)))
                .andExpect(jsonPath('$.content', iterableWithSize(arraySize)))
        where:
        params           | criteria                                              | arraySize
        "?page=0&size=3" | "{}"                                                  | 3
        "?page=1&size=3" | "{}"                                                  | 2
        "?page=2&size=3" | "{}"                                                  | 0
        "?page=0&size=5" | "{\"cardTypeId\": " + PHYSICAL.ordinal() + "}"        | 4
        "?page=0&size=5" | "{\"cardTypeId\": " + ELECTRONIC.ordinal() + "}"      | 3
        "?page=0&size=5" | "{\"brandLetter\":\"s\"}"                             | 2
        "?page=0&size=5" | "{\"priceRangeId\": " + FIFTY_HUNDRED.ordinal() + "}" | 3
        "?page=0&size=5" | "{\"priceRangeId\": " + ZERO_FIFTY.ordinal() + "}"    | 2
        "?page=0&size=5" | "{\"categoryId\": " + RESTAURANTS.ordinal() + "}"     | 2
        "?page=0&size=5" | "{\"categoryId\": " + CULTURE.ordinal() + "}"         | 0

    }


}