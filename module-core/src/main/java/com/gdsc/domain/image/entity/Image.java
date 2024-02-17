package com.gdsc.domain.image.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "image_uuid", nullable = false)
    private String uuid;

    @Builder
    public Image(String imageUrl, String uuid) {
        this.imageUrl = imageUrl;
        this.uuid = uuid;
    }
}
