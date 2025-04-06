package com.asp.neptune_bank.repositories;

import com.asp.neptune_bank.models.NomineeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NomineeDetailsRepository extends JpaRepository<NomineeDetails,Long> {
}
