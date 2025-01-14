package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호증가규칙이 데이터베이스를 따라감
	private int id;
	
	@Column(length = 20, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;
	private String website;
	private String bio; // 자기소개
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl; // 프로필 사진
	private String role; // 권한

	@ToString.Exclude
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"user"})
	private List<Image> images;
	
	private LocalDateTime createDate;
	
	@PrePersist // 디비에 insert되기 직전에 실행
	public void createDate() {
		this.createDate = createDate.now();
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", name='" + name + '\'' +
				", website='" + website + '\'' +
				", bio='" + bio + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				", gender='" + gender + '\'' +
				", profileImageUrl='" + profileImageUrl + '\'' +
				", role='" + role + '\'' +
				// ", images=" + images + 무한참조 방지 : ValidationAdvice.class -> Object arg 변수 출력시 user<->images 무한참조 일어남
				", createDate=" + createDate +
				'}';
	}
}
