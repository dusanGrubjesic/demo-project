package com.toptal.demo.demoproject.services;

import com.toptal.demo.demoproject.repo.UserInfoRepository;
import com.toptal.demo.demoproject.data.transfers.UserDto;
import com.toptal.demo.demoproject.entities.UserEntity;
import com.toptal.demo.demoproject.management.Role;
import com.toptal.demo.demoproject.management.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author dusan.grubjesic
 */
@Service
public class UserService {

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserEntity createUser(UserDto userDto) {
		UserEntity userEntity = new UserEntity();
		userEntity.setUserName(userDto.getUserName());
		userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userEntity.setFirstName(userDto.getFirstName());
		userEntity.setLastName(userDto.getLastName());
		userEntity.setEmail(userDto.getEmail());
		userEntity.setRole(Role.ROLE_USER);
		userEntity.setStatus(UserStatus.PENDING);
		userInfoRepository.save(userEntity);
		return userEntity;
	}

	public boolean validateUser(String userName, String password) {
		Optional<UserEntity> optUserEntity = userInfoRepository.findByUserNameEquals(userName);
		return optUserEntity
				.filter(s -> passwordEncoder.matches(password, s.getPassword()))
				.isPresent();
	}

	public void confirmEmail(int userId) {
		userInfoRepository.updateStatus(userId, UserStatus.ACTIVE);
	}

}
