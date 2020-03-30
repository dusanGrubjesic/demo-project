package com.toptal.demo.demoproject.entities;

import com.toptal.demo.demoproject.management.Role;
import com.toptal.demo.demoproject.management.UserStatus;
import lombok.Data;

import javax.persistence.*;

/**
 * @author dusan.grubjesic
 */
@Entity(name = "users")
@Data
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 50)
	private String userName;

	@Column(length = 500)
	private String password;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String email;

	@Column
	private Role role;

	@Column
	private UserStatus status;
}
