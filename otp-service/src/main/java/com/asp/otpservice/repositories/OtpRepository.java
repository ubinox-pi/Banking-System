package com.asp.otpservice.repositories;

import com.asp.otpservice.entity.OtpRecord;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpRecord, Long> {

    Optional<OtpRecord> findByIdentifier(String identifier);

    void deleteByIdentifier(String identifier);

    @Query("SELECT COUNT(o) > 0 FROM OtpRecord o WHERE o.otp = ?1 AND o.identifier = ?2 AND o.isUsed = false AND o.isOtpExpired = false")
    Boolean checkOtp(String otp, String identifier);

    @Modifying
    @Transactional
    @Query("UPDATE OtpRecord o SET o.isUsed = true WHERE o.otp = ?1 AND o.identifier = ?2 AND o.isUsed = false AND o.isOtpExpired = false")
    void useOtp(String otp, String identifier);

    @Query("SELECT COUNT(o) > 0 FROM OtpRecord o WHERE o.isOtpExpired = false AND o.isUsed = false AND o.identifier = ?1")
    Boolean checkActiveOtpExists(String identifier);

    @Modifying
    @Transactional
    @Query("UPDATE OtpRecord o SET o.isOtpExpired = true WHERE o.otp = ?1 AND o.identifier = ?2 AND o.isOtpExpired = false AND o.isUsed = false")
    void expireOtp(String otp, String identifier);
}

