package com.cos.photogramstart.web.dto.auth;

import javax.validation.constraints.*;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class SignUpDto {
	@Size(min=1, max=20)
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String name;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.build();
	}
}
