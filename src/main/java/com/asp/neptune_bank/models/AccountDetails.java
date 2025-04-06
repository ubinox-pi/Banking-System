package com.asp.neptune_bank.models;


import com.asp.neptune_bank.enumeration.AccountType;
import com.asp.neptune_bank.enumeration.ModeOfOperation;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long accountId;


    @Column( nullable = false, unique = true)
    private String accountNumber;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "branch_id")
//    private Branch branch;

    @Builder.Default
    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column( nullable = false)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column( nullable = false)
    private ModeOfOperation modeOfOperation;


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

