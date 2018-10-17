package pl.cba.gibcode.alabackend.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.util.List;
import java.util.Set;

@Value(staticConstructor = "of")
public class UserResponseDto {
	private String username;
	private Set<UserType> userRoles;
}
