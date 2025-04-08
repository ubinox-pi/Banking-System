package com.asp.neptune_bank.DTO;


import com.asp.neptune_bank.enumeration.Relationship;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NomineeDetailsDTO {
    private Long nomineeId;
    private String name;
    private Relationship relationship;
    private LocalDate dob;
    private String mob;
    private String email;
    private String aadhaar;
    private String address;
    private Boolean isVerified;
}
