package pl.cba.gibcode.alabackend.user.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class UserDetailsDto {
	private String username;
	private String fullName;
	private String address;
}
