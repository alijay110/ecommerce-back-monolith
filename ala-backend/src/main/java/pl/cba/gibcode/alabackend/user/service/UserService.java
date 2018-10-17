package pl.cba.gibcode.alabackend.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cba.gibcode.alabackend.shared.BusinessException;
import pl.cba.gibcode.alabackend.user.mapper.UserMapper;
import pl.cba.gibcode.alabackend.user.model.*;
import pl.cba.gibcode.alabackend.user.repository.UserDetailsRepository;
import pl.cba.gibcode.alabackend.user.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final UserDetailsRepository userDetailsRepository;

	@Transactional(readOnly = true)
	public Page<UserResponseDto> findAll(Pageable pageable) {
		return userRepository.findAll(pageable).map(UserMapper::map);
	}

	@Transactional
	public UserResponseDto registerOrAddRole(UserRequestDto userRequestDto){
		Optional<User> user = userRepository.findByUsername(userRequestDto.getUsername());
		if(user.isPresent()){
			if(user.get().getUserTypes().contains(userRequestDto.getUserType())){
				throw new BusinessException(String.format("User with this username %s and role %s already exists", userRequestDto.getUsername(), userRequestDto.getUserType()));
			} else{
				User foundUser = user.get();
				foundUser.getUserTypes().add(userRequestDto.getUserType());
				return UserMapper.map(foundUser);
			}
		}else {
			User newUser = new User();
			newUser.setUsername(userRequestDto.getUsername());
			newUser.setUserTypes(Collections.singleton(userRequestDto.getUserType()));
			User savedUser = userRepository.save(newUser);
			UserDetails userDetails = new UserDetails();
			userDetails.setUser(savedUser);
			userDetails.setFullName(userRequestDto.getFullName());
			userDetails.setAddress(userRequestDto.getAddress());
			userDetailsRepository.save(userDetails);
			return UserMapper.map(savedUser);
		}

	}

	@Transactional
	public UserResponseDto login(String username){
		return UserMapper.map(userRepository.findByUsername(username).orElseThrow( () -> new BusinessException("No such username found")));
	}

	@Transactional
	public UserResponseDto loginAsAdmin(String username){
		UserResponseDto userResponseDto = login(username);
		if(!userResponseDto.getUserRoles().contains(UserType.ADMIN)){
			throw new BusinessException(String.format("User %s is not an admin!", username));
		}
		return userResponseDto;
	}

	@Transactional
	public UserResponseDto loginAsSeller(String username){
		UserResponseDto userResponseDto = login(username);
		if(!userResponseDto.getUserRoles().contains(UserType.SELLER)){
			throw new BusinessException(String.format("User %s is not a seller!", username));
		}
		return userResponseDto;
	}

	@Transactional
	public UserResponseDto loginAsBuyer(String username){
		UserResponseDto userResponseDto = login(username);
		if(!userResponseDto.getUserRoles().contains(UserType.BUYER)){
			throw new BusinessException(String.format("User %s is not a buyer!", username));
		}
		return userResponseDto;
	}


}
