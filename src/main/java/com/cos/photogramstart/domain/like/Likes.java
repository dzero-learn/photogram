package com.cos.photogramstart.domain.like;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
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
        uniqueConstraints = {
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

    private int userId;
    private int imageId;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = createDate.now();
    }
}
