package com.asp.neptune_bank.service;


import com.asp.neptune_bank.models.AccountDetails;
import com.asp.neptune_bank.models.Branch;
import com.asp.neptune_bank.models.Users;
import com.asp.neptune_bank.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private UserRepository userRepository;
    private ContactDetailsRepository contactDetailsRepository;
    private NomineeDetailsRepository nomineeDetailsRepository;
    private AccountDetailsRepository accountDetailsRepository;
    private BranchRepository branchRepository;

    @Autowired
    public void setBranchRepository(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

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
//        branchRepository.save(users.getAccountDetails().getBranch());

        // Extract and persist Branch
        Branch branch = users.getAccountDetails().getBranch();
        if (branch.getBranchId() == null) {
            branch = branchRepository.save(branch);  // Save only if it's a new branch
        }

        // Set branch back to AccountDetails (ensures it's a managed entity)
        AccountDetails accountDetails = users.getAccountDetails();
        accountDetails.setBranch(branch);

        return userRepository.save(users);
    }

}
