package com.asp.neptune_bank.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDetailsDTO {
    private Long contactId;
    private String mobileNumber;
    private String email;
    private String communicationAddress;
    private String permanentAddress;
    private String city;
    private String state;
    private String zip;
    private String landmark;
    private String country;
    private String alternateMobileNumber;
    private String alternateEmail;
}
