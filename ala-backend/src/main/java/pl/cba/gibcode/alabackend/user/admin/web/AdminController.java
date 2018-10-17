package pl.cba.gibcode.alabackend.user.admin.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.cba.gibcode.alabackend.brand.model.BrandCriteriaDto;
import pl.cba.gibcode.alabackend.brand.model.BrandRequestDto;
import pl.cba.gibcode.alabackend.brand.model.BrandResponseDto;
import pl.cba.gibcode.alabackend.brand.service.BrandService;
import pl.cba.gibcode.alabackend.card.model.CardCriteriaDto;
import pl.cba.gibcode.alabackend.card.model.CardResponseDto;
import pl.cba.gibcode.alabackend.card.service.CardService;
import pl.cba.gibcode.alabackend.user.model.User;
import pl.cba.gibcode.alabackend.user.model.UserResponseDto;
import pl.cba.gibcode.alabackend.user.service.UserService;

@RestController
@RequiredArgsConstructor
@Api
@RequestMapping("api")
public class AdminController {

	private final CardService cardService;
	private final UserService userService;
	private final BrandService brandService;

	@ApiOperation(
			value = "Get all cards for admin",
			notes = "This call is used to get all cards filtered for admin",
			httpMethod = "POST")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Cards returned"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Unknown error")
	})
	@PostMapping("admin/cards")
	public ResponseEntity<Page<CardResponseDto>> findAllCards(Pageable pageable, @RequestBody CardCriteriaDto criteria, @RequestParam String username) {
		//pretend validation for admin
		userService.loginAsAdmin(username);
		return ResponseEntity.ok(cardService.findAllForAdmin(pageable, criteria));
	}

	@ApiOperation(
			value = "Get all brands for admin",
			notes = "This call is used to get all brands filtered",
			httpMethod = "POST")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Brands returned"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Unknown error")
	})
	@PostMapping("admin/brands")
	public ResponseEntity<Page<BrandResponseDto>> getBrandBy(Pageable pageable, @RequestBody BrandCriteriaDto criteria, @RequestParam String username) {
		//pretend validation for admin
		userService.loginAsAdmin(username);
		return ResponseEntity.ok(brandService.findAllForAdmin(pageable, criteria));
	}

	@ApiOperation(
			value = "Update brand by admin",
			notes = "This call is used to update a brand",
			httpMethod = "PUT")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Updated Brand returned"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Unknown error")
	})
	@PutMapping("admin/brands")
	public ResponseEntity<BrandResponseDto> updateBrand(@RequestBody BrandRequestDto dto, @RequestParam String username) {
		//pretend validation for admin
		userService.loginAsAdmin(username);
		return ResponseEntity.ok(brandService.updateBrand(dto));
	}


	@ApiOperation(
			value = "Delete brand by admin",
			notes = "This call is used to delete a brand",
			httpMethod = "DELETE")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Deleted Brand returned"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Unknown error")
	})
	@DeleteMapping("admin/brands")
	public ResponseEntity<BrandResponseDto> deleteBrand(@RequestBody BrandRequestDto dto, @RequestParam String username) {
		//pretend validation for admin
		userService.loginAsAdmin(username);
		return ResponseEntity.ok(brandService.deleteBrand(dto));
	}

	@ApiOperation(
			value = "Get all users for admin",
			notes = "This call is used to get all users for admin",
			httpMethod = "GET")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Users returned"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Unknown error")
	})
	@GetMapping("admin/users")
	public ResponseEntity<Page<UserResponseDto>> findAllUsers(Pageable pageable, @RequestParam String username) {
		userService.loginAsAdmin(username);
		return ResponseEntity.ok(userService.findAll(pageable));
	}

	@ApiOperation(
			value = "Validate card for admin",
			httpMethod = "POST")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Card returned"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Unknown error")
	})
	@PostMapping("admin/cards/validate")
	public ResponseEntity<CardResponseDto> validateCard(@RequestBody Long cardId, @RequestParam String username) {
		//pretend validation for admin
		return ResponseEntity.ok(cardService.validate(cardId, userService.loginAsAdmin(username)));
	}

	@ApiOperation(
			value = "Delete card for admin",
			httpMethod = "DELETE")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Card returned"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Unknown error")
	})
	@DeleteMapping("admin/cards")
	public ResponseEntity<CardResponseDto> deleteCard(@RequestBody Long cardId,@RequestParam String username) {
		//pretend validation for admin
		return ResponseEntity.ok(cardService.deleteCard(cardId, userService.loginAsAdmin(username)));
	}
}
