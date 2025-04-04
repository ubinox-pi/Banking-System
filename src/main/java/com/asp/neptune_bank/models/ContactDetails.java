package com.asp.neptune_bank.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(updatable = false)
    private Long contactId;

    @NotBlank(message = "Mobile Number cannot be blank")
    @Column(unique = true, nullable = false)
    private String mobileNumber;

    @NotBlank(message = "Email cannot be blank")
    @Column(unique = true, nullable = false)
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Communication Address cannot be blank")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String communicationAddress;

    @NotBlank(message = "Permanent Address cannot be blank")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String permanentAddress;

    @NotBlank(message = "City cannot be blank")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "State cannot be blank")
    @Column(nullable = false)
    private String state;

    @NotBlank(message = "Pincode cannot be blank")
    @Column(nullable = false)
    private String zip;

    @NotBlank(message = "Landmark cannot be blank")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String landmark;

    @NotBlank(message = "Country cannot be blank")
    @Column(nullable = false)
    private String country;

    @Builder.Default
    private String alternateMobileNumber = null;

    @Builder.Default
    @Email(message = "Email should be valid")
    private String alternateEmail = null;

    @Column(updatable = false)
    //@PastOrPresent
    private LocalDateTime createdAt;

    //@FutureOrPresent
    private LocalDateTime updatedAt;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}

