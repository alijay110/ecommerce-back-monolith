package pl.cba.gibcode.alabackend.user.seller.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.cba.gibcode.alabackend.card.model.CardRequestDto;
import pl.cba.gibcode.alabackend.card.model.CardResponseDto;
import pl.cba.gibcode.alabackend.card.service.CardService;
import pl.cba.gibcode.alabackend.transaction.model.SummaryDto;
import pl.cba.gibcode.alabackend.user.model.UserDetailsDto;
import pl.cba.gibcode.alabackend.user.seller.service.SellerService;
import pl.cba.gibcode.alabackend.user.service.UserService;

@RestController
@RequiredArgsConstructor
@Api
@RequestMapping("api")
public class SellerController {

	private final UserService userService;
	private final CardService cardService;
	private final SellerService sellerService;

	@ApiOperation(
			value = "Create a card as a seller",
			httpMethod = "POST")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Card returned"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Unknown error")
	})
	@PostMapping("seller/card")
	public ResponseEntity<CardResponseDto> createCard(
			@RequestBody CardRequestDto cardRequestDto,
			@RequestParam String username) {
		return ResponseEntity.ok(cardService.createCard(cardRequestDto, userService.loginAsSeller(username)));
	}


	@ApiOperation(
			value = "Update a card as a seller",
			httpMethod = "PUT")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Card returned"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Unknown error")
	})
	@PutMapping("seller/card")
	public ResponseEntity<CardResponseDto> updateCard(
			@RequestBody CardRequestDto cardRequestDto,
			@RequestParam String username) {
		return ResponseEntity.ok(cardService.updateCard(cardRequestDto, userService.loginAsSeller(username)));
	}

	@ApiOperation(
			value = "Send the card",
			httpMethod = "PUT")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Card returned"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Unknown error")
	})
	@PutMapping("seller/card/send")
	public ResponseEntity<UserDetailsDto> sendCard(
			@RequestBody Long transactionId,
			@RequestParam String username) {
		return ResponseEntity.ok(sellerService.sendCard(transactionId,userService.loginAsSeller(username)));
	}


	@ApiOperation(
			value = "Delete a card as a seller",
			httpMethod = "DELETE")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Card returned"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Unknown error")
	})
	@DeleteMapping("seller/card")
	public ResponseEntity<CardResponseDto> deleteCard(
			@RequestBody Long cardId,
			@RequestParam String username) {
		return ResponseEntity.ok(cardService.deleteCard(cardId, userService.loginAsSeller(username)));
	}

	@ApiOperation(
			value = "Get all transactions for a seller",
			httpMethod = "GET")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of transactions returned"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Unknown error")
	})
	@GetMapping("seller/transactions")
	public ResponseEntity<Page<SummaryDto>> listTransactions(Pageable pageable, @RequestParam String username){
		return ResponseEntity.ok(sellerService.findAllTransactionsForSeller(pageable, userService.loginAsSeller(username)));
	}


}
