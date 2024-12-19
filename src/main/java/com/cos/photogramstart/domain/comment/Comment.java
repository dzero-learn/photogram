package com.cos.photogramstart.domain.comment;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnoreProperties("user")
    @JoinColumn(name = "imageId", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Image image;

    @JsonIgnoreProperties("images")
    @JoinColumn(name = "userId", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(length = 100, nullable = false)
    private String content;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = createDate.now();
    }
}
