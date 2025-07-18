package com.asp.otpservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "idx_identifier", columnList = "identifier"),
        @Index(name = "idx_otp", columnList = "otp")
})
public class OtpRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String identifier;

    @Column(nullable = false)
    private String otp;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiryTime;

    @Builder.Default
    private boolean isUsed = false;

    @Builder.Default
    private boolean isOtpExpired = false;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public OtpRecord(String identifier, String otp, boolean isUsed, boolean isOtpExpired, Instant expiry) {
        this.identifier = identifier;
        this.otp = otp;
        this.createdAt = LocalDateTime.now();
        this.expiryTime = LocalDateTime.ofInstant(expiry, ZoneId.systemDefault());
        this.isUsed = isUsed;
        this.isOtpExpired = isOtpExpired;
    }
}
