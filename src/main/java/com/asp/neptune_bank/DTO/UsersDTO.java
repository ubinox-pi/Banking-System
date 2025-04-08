package com.asp.neptune_bank.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String fatherName;
    private String motherName;
    private String maritalStatus;
    private String spouseName;
    private String occupation;
    private String aadhar;
    private String pan;
    private Double accountInterestRate;
    private Boolean isKycCompleted;
    private Boolean isVerified;
    private Boolean isActive;
    private Boolean isBlocked;

    private ContactDetailsDTO contactDetails;
    private AccountDetailsDTO accountDetails;
    private NomineeDetailsDTO nomineeDetails;


}
