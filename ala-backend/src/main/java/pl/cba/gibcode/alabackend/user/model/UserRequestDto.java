package pl.cba.gibcode.alabackend.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class UserRequestDto {
	@NotEmpty
	private String username;
	@NotNull
	private UserType userType;
	@NotEmpty
	private String fullName;
	@NotEmpty
	private String address;

}
