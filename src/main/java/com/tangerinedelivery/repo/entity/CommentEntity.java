package com.tangerinedelivery.repo.entity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data

public class CommentEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @ManyToOne(fetch = FetchType.EAGER)
        private ProductEntity product;
        @Column(nullable = false)
        private String username;
        @Column(nullable = false)
        private Long userId;
        @Column(columnDefinition = "text", nullable = false)
        private String message;
        @Column(updatable = false)
        private LocalDateTime createdDate;

        public CommentEntity() {
        }

        @PrePersist
        protected void onCreate()
        {
            this.createdDate = LocalDateTime.now();
        }

}
