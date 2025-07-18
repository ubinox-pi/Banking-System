package com.asp.otpservice.service;

import com.asp.otpservice.entity.OtpRecord;
import com.asp.otpservice.otp.OtpGenerator;
import com.asp.otpservice.repositories.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OtpService {

    private final JavaMailSender mailSender;
    private final OtpRepository otpRepository;

    @Value("bd83eaff-0466-41ed-8ec8-a3234b09f36e")
    private String apiKey;

    @Value("68594bd7a5fdde60955c7fdc")
    private String deviceId;

    private static final long OTP_EXPIRY_SECONDS = 300;

    @Autowired
    public OtpService(JavaMailSender mailSender, OtpRepository otpRepository) {
        this.mailSender = mailSender;
        this.otpRepository = otpRepository;
    }

    public ResponseEntity<String> sendEmailOtp(String toEmail) {
        String otp = OtpGenerator.generateOtp(6);

        if (otpRepository.checkActiveOtpExists(toEmail)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("An active OTP already exists.");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Neptune Bank OTP");
        message.setText("Dear user,\n\nYour OTP is: " + otp + "\nIt will expire in 5 minutes.\n\nRegards,\nNeptune Bank");

        try {
            mailSender.send(message);

            otpRepository.deleteByIdentifier(toEmail);
            otpRepository.save(OtpRecord.builder()
                    .identifier(toEmail)
                    .otp(otp)
                    .isUsed(false)
                    .isOtpExpired(false)
                    .expiryTime(LocalDateTime.now().plusSeconds(OTP_EXPIRY_SECONDS))
                    .build());

            return ResponseEntity.ok("OTP sent to " + toEmail);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Email OTP sent, but failed to confirm status due to error: " + ex.getMessage());
        }
    }

    public ResponseEntity<String> sendSmsOtp(String phoneNumber) {
        String otp = OtpGenerator.generateOtp(6);

        if (otpRepository.checkActiveOtpExists(phoneNumber)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("An active OTP already exists.");
        }

        String message = "Dear Customer, your Neptune Bank OTP is: " + otp + ". Please do not share it with anyone.";
        String url = "https://api.textbee.dev/api/v1/gateway/devices/" + deviceId + "/send-sms";

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = Map.of(
                "recipients", List.of(phoneNumber),
                "message", message
        );

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = new RestTemplate().exchange(
                    url, HttpMethod.POST, requestEntity, String.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new IllegalStateException("SMS API responded with non-2xx: " + response.getStatusCode());
            }

            otpRepository.deleteByIdentifier(phoneNumber);
            otpRepository.save(OtpRecord.builder()
                    .identifier(phoneNumber)
                    .otp(otp)
                    .isUsed(false)
                    .isOtpExpired(false)
                    .expiryTime(LocalDateTime.now().plusSeconds(OTP_EXPIRY_SECONDS))
                    .build());

            return ResponseEntity.ok("OTP sent to " + phoneNumber);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SMS OTP sent, but confirmation failed: " + ex.getMessage());
        }
    }

    public ResponseEntity<String> verifyOtp(String identifier, String inputOtp) {
        Optional<OtpRecord> otpOptional = otpRepository.findByIdentifier(identifier);

        if (otpOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No OTP sent to this identifier.");
        }

        OtpRecord record = otpOptional.get();

        if (record.getExpiryTime().isBefore(LocalDateTime.now(ZoneId.systemDefault()))) {
            otpRepository.expireOtp(record.getOtp(), identifier);
            return ResponseEntity.status(HttpStatus.GONE).body("OTP has expired.");
        }

        if (!record.getOtp().equals(inputOtp)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP.");
        }

        otpRepository.useOtp(inputOtp, identifier);
        return ResponseEntity.ok("OTP verified successfully.");
    }
}
