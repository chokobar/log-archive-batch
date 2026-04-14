package com.chokobar.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(
        name = "api_call_log",
        indexes = {
                @Index(name = "idx_api_call_log_created_at", columnList = "createdAt"),
                @Index(name = "idx_api_call_log_status_code", columnList = "statusCode")
        }
)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiCallLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String apiPath;

    @Column(nullable = false, length = 20)
    private String method;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String requestHeaders;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String requestBody;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String responseBody;

    @Column(nullable = false)
    private Integer statusCode;

    @Column
    private Long durationMs;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}