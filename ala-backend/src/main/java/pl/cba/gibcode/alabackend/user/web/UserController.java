package pl.cba.gibcode.alabackend.user.web;

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
import pl.cba.gibcode.alabackend.card.model.CardCriteriaDto;
import pl.cba.gibcode.alabackend.card.model.CardResponseDto;
import pl.cba.gibcode.alabackend.user.model.User;
import pl.cba.gibcode.alabackend.user.model.UserRequestDto;
import pl.cba.gibcode.alabackend.user.model.UserResponseDto;
import pl.cba.gibcode.alabackend.user.repository.UserRepository;
import pl.cba.gibcode.alabackend.user.service.UserService;

@RestController
@RequiredArgsConstructor
@Api
@RequestMapping("api")
public class UserController {

	private final UserService userService;

	@ApiOperation(
			value = "Registers user",
			httpMethod = "POST")
	@ApiResponses({
			@ApiResponse(code = 200, message = "User returned"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Unknown error")
	})
	@PostMapping("user/register")
	public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto userRequestDto) {
		return ResponseEntity.ok(userService.registerOrAddRole(userRequestDto));
	}


	@ApiOperation(
			value = "Login user",
			httpMethod = "POST")
	@ApiResponses({
			@ApiResponse(code = 200, message = "User returned"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Unknown error")
	})
	@PostMapping("user/login")
	public ResponseEntity<UserResponseDto> loginUser(@RequestBody String username) {
		return ResponseEntity.ok(userService.login(username));
	}

}




