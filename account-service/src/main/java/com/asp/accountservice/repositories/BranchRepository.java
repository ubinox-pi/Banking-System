package com.asp.accountservice.repositories;

import com.asp.accountservice.models.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {


    Optional<Branch> findByBranchCode(String branchCode);

}
