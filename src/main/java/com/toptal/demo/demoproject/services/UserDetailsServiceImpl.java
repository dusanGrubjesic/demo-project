package com.toptal.demo.demoproject.services;

import com.toptal.demo.demoproject.entities.UserEntity;
import com.toptal.demo.demoproject.repo.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * @author dusan.grubjesic
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> optUserEntity = userInfoRepository.findByUserName(username);
		if (optUserEntity.isPresent()) {
			UserEntity userEntity = optUserEntity.get();
			return new User(
					userEntity.getUserName(),
					userEntity.getPassword(),
					Collections.singletonList(
							new SimpleGrantedAuthority(userEntity.getRole().name())));
		} else {
			return null;
		}
	}
}
