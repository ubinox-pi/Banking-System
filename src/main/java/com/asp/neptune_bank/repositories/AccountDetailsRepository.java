package com.asp.neptune_bank.repositories;

import com.asp.neptune_bank.models.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails,Long> {
}
