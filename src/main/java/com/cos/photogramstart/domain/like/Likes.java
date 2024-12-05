package com.cos.photogramstart.domain.like;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(
        uniqueConstraints = { // 유니크 제약조건
                @UniqueConstraint(
                        name="likes_uk",
                        columnNames = {
                                "userId",
                                "imageId"
                        }
                )
        }
)
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호증가규칙이 데이터베이스를 따라감
    private int id;

    @JsonIgnoreProperties("likes")
    @JoinColumn(name="imageId") //imageId로 컬럼명을 만들어라.
    @ManyToOne
    private Image imageId; // like(n) : image(1)

    @JsonIgnoreProperties("images")
    @JoinColumn(name="userId") // userId로 컬럼명을 만들어라.
    @ManyToOne // ManyToOne는 기본전략이 eager 전략이다.
    private User userId; // like(n) : user(1)

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = createDate.now(); // 네이티브쿼리에서는 이게 안 먹히니 쿼리에 직접 now() 적어줘야함, likesRepository 참고.
    }
}
