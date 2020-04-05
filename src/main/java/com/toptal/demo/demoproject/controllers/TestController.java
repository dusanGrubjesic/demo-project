package com.toptal.demo.demoproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dusan.grubjesic
 */
@RestController
@RequestMapping("/test")
public class TestController {

	@GetMapping
	public String test() {
return "success";
	}
}
