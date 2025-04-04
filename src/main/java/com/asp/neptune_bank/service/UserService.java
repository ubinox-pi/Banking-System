package com.asp.neptune_bank.service;


import com.asp.neptune_bank.models.Users;
import com.asp.neptune_bank.repositories.ContactDetailsRepository;
import com.asp.neptune_bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private ContactDetailsRepository contactDetailsRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setContactDetailsRepository(ContactDetailsRepository contactDetailsRepository) {
        this.contactDetailsRepository = contactDetailsRepository;
    }

    public Users createUser(Users users){
        users.setContactDetails(contactDetailsRepository.save(users.getContactDetails()));
        return userRepository.save(users);
    }

}
