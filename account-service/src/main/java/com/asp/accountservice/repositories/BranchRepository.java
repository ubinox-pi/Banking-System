package com.asp.accountservice.repositories;

import com.asp.accountservice.models.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {


    Optional<Branch> findByBranchCode(String branchCode);


    @Query("SELECT COUNT(b) > 0 FROM Branch b WHERE b.branchCode = ?1")
    boolean existsByBranchCode(String branchCode);


    @Query("SELECT b.branchId FROM Branch b WHERE b.branchCode = ?1")
    Optional<Long> findBranchIdByBranchCode(String branchCode);
}
