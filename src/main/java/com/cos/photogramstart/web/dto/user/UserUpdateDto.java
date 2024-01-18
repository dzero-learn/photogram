package com.cos.photogramstart.web.dto.user;

import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateDto {
	@NotBlank
	private String name;
	@NotBlank
	private String password;
	private String website;
	private String bio; // 자기소개
	private String phone; // 자기소개
	private String gender;
	
	public User toEntity() {
		return User.builder()
				.name(name)
				.password(password)
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}
