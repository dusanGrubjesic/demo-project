package com.toptal.demo.demoproject.controllers;

import com.toptal.demo.demoproject.repo.UserInfoRepository;
import com.toptal.demo.demoproject.data.transfers.UserDto;
import com.toptal.demo.demoproject.entities.UserEntity;
import com.toptal.demo.demoproject.services.MailService;
import com.toptal.demo.demoproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dusan.grubjesic
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private MailService mailService;

	@GetMapping
	public boolean userNameAvailability(@RequestParam(value = "user_name") String userName) {
		return userInfoRepository.existsUserEntityByUserName(userName);
	}

	@PostMapping
	public void createUser(@RequestBody UserDto userDto) {
		UserEntity userEntity = userService.createUser(userDto);
		mailService.generateMail(userEntity);
	}

	@GetMapping("/mail/validate/{referenceNumber}")
	public void verifyMail(@PathVariable String referenceNumber) {
		mailService.validateMail(referenceNumber);
	}

	@GetMapping("/mail/resend/{mail}")
	public void resendMail(@PathVariable String mail) {
		mailService.resendVerificationMail(mail);
	}
}
