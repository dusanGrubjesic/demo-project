package com.toptal.demo.demoproject.data.transfers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author dusan.grubjesic
 */
@Data
public class UserDto {

	@JsonProperty("user_name")
	private String userName;

	private String password;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;

	private String email;
}
