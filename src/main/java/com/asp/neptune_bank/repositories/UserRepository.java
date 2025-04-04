package com.asp.neptune_bank.repositories;

import com.asp.neptune_bank.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users , Long> {
}
