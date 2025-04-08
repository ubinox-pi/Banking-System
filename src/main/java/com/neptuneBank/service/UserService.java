package com.neptuneBank.service;

import com.neptuneBank.DTOs.usersDTO.UsersDTO;
import com.neptuneBank.exception.usersException.UserException;
import com.neptuneBank.models.ContactDetails;
import com.neptuneBank.models.ENUM.MaritalStatus;
import com.neptuneBank.models.Users;
import com.neptuneBank.repository.ContactDetailsRepository;
import com.neptuneBank.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;


    private ContactDetailsRepository contactDetailsRepository;

    @Autowired
    public void setContactDetailsRepository(ContactDetailsRepository contactDetailsRepository) {
        this.contactDetailsRepository = contactDetailsRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UsersDTO registerUser(UsersDTO request) throws UserException {
        if (request == null || request.getContactDetails() == null) {
            throw new UserException("User and contact details cannot be null.");
        }

        if (request.getMaritalStatus() == MaritalStatus.MARRIED && request.getSpouseName() == null) {
            throw new UserException("Spouse name is required for married users.");
        }

        ContactDetails contactDetails = new ContactDetails();
        contactDetails.fromDTO(request.getContactDetails());

        contactDetails = contactDetailsRepository.save(contactDetails);
        if (contactDetails == null) {
            throw new UserException("Error while saving user contact details.");
        }
        request.setContactDetails(contactDetails.toDTO());

        Users users = new Users();
        users.fromDTO(request);

        users = userRepository.save(users);

        if (users == null) {
            throw new UserException("Error while saving user.");
        }
        return users.toDTO();
    }


}
