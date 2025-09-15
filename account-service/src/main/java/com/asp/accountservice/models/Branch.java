package com.asp.accountservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(updatable = false)
    private Long branchId;

    @NotBlank(message = "Branch code is required")
    @Column(nullable = false, unique = true)
    private String branchCode;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonManagedReference
    private List<Account> accounts = new ArrayList<>();

    @NotBlank(message = "Branch Name cannot be blank")
    @Column(nullable = false)
    private String branchName;

    @NotBlank(message = "Branch Address cannot be blank")
    @Column(nullable = false)
    private String branchAddress;

    @NotBlank(message = "Branch City cannot be blank")
    @Column(nullable = false)
    private String branchCity;

    @NotBlank(message = "Branch State cannot be blank")
    @Column(nullable = false)
    private String branchState;

    @NotBlank(message = "Branch Zip cannot be blank")
    @Column(nullable = false)
    private String branchZip;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}
