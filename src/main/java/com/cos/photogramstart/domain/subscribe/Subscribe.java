package com.cos.photogramstart.domain.subscribe;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(
		uniqueConstraints = {
				@UniqueConstraint(
						name="subscribe_uk",
						columnNames = {
								"fromUserId",
								"toUserId"
								}
						)
				}
		)
public class Subscribe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호증가규칙이 데이터베이스를 따라감
	private int id;
	
	@JoinColumn(name="fromUserId")
	@ManyToOne
	private User fromUserId;
	
	@JoinColumn(name="toUserId")
	@ManyToOne
	private User toUserId;
	
	private LocalDateTime createDate;
	
	@PrePersist
	public void createDate() {
		this.createDate = createDate.now();
	}
}
