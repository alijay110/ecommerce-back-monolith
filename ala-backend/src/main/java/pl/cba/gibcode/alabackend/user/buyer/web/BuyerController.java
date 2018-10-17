package pl.cba.gibcode.alabackend.user.buyer.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.cba.gibcode.alabackend.transaction.model.SummaryDto;
import pl.cba.gibcode.alabackend.user.buyer.service.BuyerService;
import pl.cba.gibcode.alabackend.user.service.UserService;

@RestController
@RequiredArgsConstructor
@Api
@RequestMapping("api")
public class BuyerController {

	private final BuyerService buyerService;
	private final UserService userService;

	@ApiOperation(
			value = "Checkout a card as a buyer",
			httpMethod = "POST")
	@ApiResponses({
			@ApiResponse(code = 200, message = "New Transaction returned"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Unknown error")
	})
	@PostMapping("buyer/checkout")
	public ResponseEntity<SummaryDto> checkout(
			@RequestBody Long cardId,
			@RequestParam String username) {
		return ResponseEntity.ok(buyerService.checkout(cardId, userService.loginAsBuyer(username)));
	}

	@ApiOperation(
			value = "Pay a card as a buyer",
			httpMethod = "POST")
	@ApiResponses({
			@ApiResponse(code = 200, message = "New Transaction returned"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Unknown error")
	})
	@PostMapping("buyer/pay")
	public ResponseEntity<SummaryDto> pay(
			@RequestBody Long transactionId,
			@RequestParam String username) {
		return ResponseEntity.ok(buyerService.pay(transactionId, userService.loginAsBuyer(username)));
	}

	@ApiOperation(
			value = "Get all transactions for a buyer",
			httpMethod = "GET")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of transactions returned"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Unknown error")
	})
	@GetMapping("buyer/transactions")
	public ResponseEntity<Page<SummaryDto>> listTransactions(Pageable pageable, @RequestParam String username){
		return ResponseEntity.ok(buyerService.findAllTransactionsForBuyer(pageable, userService.loginAsBuyer(username)));
	}

}
