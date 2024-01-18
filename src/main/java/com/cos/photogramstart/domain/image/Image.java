package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String caption;
	private String postImageUrl;

	@JsonIgnoreProperties("images") // Image Entity이기 때문에 user클래스 안에 images멤버변수는 데이터가 중복되므로 가지고 올 필요x
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.EAGER)
	private User user; // 쿼리 짤 때, 굳이 user join 필요x 이미지 조회할때 이 필드 때문에 user정보 같이 들고 온다!

	private LocalDateTime createDate;

	@PrePersist
	public void createDate() {
		this.createDate = createDate.now();
	}
}
