package pl.cba.gibcode.alabackend.user.mapper;

import pl.cba.gibcode.alabackend.user.model.User;
import pl.cba.gibcode.alabackend.user.model.UserResponseDto;

public class UserMapper {


	public static UserResponseDto map(User user){
		return UserResponseDto.of(user.getUsername(), user.getUserTypes());
	}
}
