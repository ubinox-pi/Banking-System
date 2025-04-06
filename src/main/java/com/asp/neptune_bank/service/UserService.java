package com.asp.neptune_bank.service;


import com.asp.neptune_bank.models.Users;
import com.asp.neptune_bank.repositories.AccountDetailsRepository;
import com.asp.neptune_bank.repositories.ContactDetailsRepository;
import com.asp.neptune_bank.repositories.NomineeDetailsRepository;
import com.asp.neptune_bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private UserRepository userRepository;
    private ContactDetailsRepository contactDetailsRepository;
    private NomineeDetailsRepository nomineeDetailsRepository;
    private AccountDetailsRepository accountDetailsRepository;

    @Autowired
    public void setAccountDetailsRepository(AccountDetailsRepository accountDetailsRepository) {
        this.accountDetailsRepository = accountDetailsRepository;
    }

    @Autowired
    public void setNomineeDetailsRepository(NomineeDetailsRepository nomineeDetailsRepository) {
        this.nomineeDetailsRepository = nomineeDetailsRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setContactDetailsRepository(ContactDetailsRepository contactDetailsRepository) {
        this.contactDetailsRepository = contactDetailsRepository;
    }

    @Transactional
    public Users createUser(Users users) {

        users.setContactDetails(contactDetailsRepository.save(users.getContactDetails()));
        users.setNomineeDetails(nomineeDetailsRepository.save(users.getNomineeDetails()));
        users.setAccountDetails(accountDetailsRepository.save(users.getAccountDetails()));

        return userRepository.save(users);
    }

}
