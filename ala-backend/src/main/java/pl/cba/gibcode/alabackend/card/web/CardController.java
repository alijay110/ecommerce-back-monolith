package pl.cba.gibcode.alabackend.card.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.cba.gibcode.alabackend.brand.model.BrandCriteriaDto;
import pl.cba.gibcode.alabackend.brand.model.BrandResponseDto;
import pl.cba.gibcode.alabackend.card.model.CardCriteriaDto;
import pl.cba.gibcode.alabackend.card.model.CardResponseDto;
import pl.cba.gibcode.alabackend.card.service.CardService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api
@RequestMapping("api")
public class CardController {

	private final CardService cardService;

	@ApiOperation(
			value = "Get all cards for sale",
			notes = "This call is used to get all cards filtered",
			httpMethod = "POST")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Cards returned"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Unknown error")
	})
	@PostMapping("cards/list")
	public ResponseEntity<Page<CardResponseDto>> getCardsBy(Pageable pageable, @RequestBody CardCriteriaDto criteria) {
		return ResponseEntity.ok(cardService.findAll(pageable, criteria));
	}

}

//buyer controller: add card with brand and price range and type to basket (no matter what id),
					//buy (check if it is still available in this range, assign it to user)
					//list all user cards
					//register user
