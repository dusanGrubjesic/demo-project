package com.toptal.demo.demoproject.controllers;

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
	private UserService userService;
	@Autowired
	private MailService mailService;

	@PostMapping
	public void createUser(@RequestBody UserDto userDto) {
		UserEntity userEntity = userService.createUser(userDto);
		mailService.generateMail(userEntity);

	}


	@GetMapping("/verify_mail/{referenceNumber}")
	public void verifyMail(@PathVariable String referenceNumber) {
		mailService.
	}
}
