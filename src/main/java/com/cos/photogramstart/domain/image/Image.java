package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.like.Likes;
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

	// 이미지좋아요
	@OneToMany(mappedBy = "imageId", fetch = FetchType.LAZY)
	private List<Likes> likes;

	// 댓글
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy = "image", fetch = FetchType.LAZY) // mappedBy는 fk에 대한 자바변수를 적어줘야함(comment 객체 안에 image객체 변수명)
	@OrderBy("createDate DESC")
	private List<Comment> comments;

	@Transient // 데이터베이스에 컬럼을 생성하지 않음
	private boolean likeState;

	@Transient
	private int likeCount;

	private LocalDateTime createDate;

	@PrePersist
	public void createDate() {
		this.createDate = createDate.now();
	}
}
