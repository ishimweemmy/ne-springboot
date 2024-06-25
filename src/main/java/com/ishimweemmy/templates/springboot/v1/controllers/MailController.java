package com.ishimweemmy.templates.springboot.v1.controllers;

import com.ishimweemmy.templates.springboot.v1.standalone.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/send-reset-password")
    public ResponseEntity<String> sendResetPasswordMail(@RequestParam String to,
                                                        @RequestParam String fullName,
                                                        @RequestParam String resetCode) {
        mailService.sendResetPasswordMail(to, fullName, resetCode);
        return ResponseEntity.ok("Password reset email sent successfully");
    }

    @PostMapping("/send-activate-account")
    public ResponseEntity<String> sendActivateAccountEmail(@RequestParam String to,
                                                           @RequestParam String fullName,
                                                           @RequestParam String verificationCode) {
        mailService.sendActivateAccountEmail(to, fullName, verificationCode);
        return ResponseEntity.ok("Account activation email sent successfully");
    }

    @PostMapping("/send-account-verified")
    public ResponseEntity<String> sendAccountVerifiedSuccessfullyEmail(@RequestParam String to,
                                                                       @RequestParam String fullName) {
        mailService.sendAccountVerifiedSuccessfullyEmail(to, fullName);
        return ResponseEntity.ok("Account verification success email sent successfully");
    }

    @PostMapping("/send-password-reset-success")
    public ResponseEntity<String> sendPasswordResetSuccessfully(@RequestParam String to,
                                                                @RequestParam String fullName) {
        mailService.sendPasswordResetSuccessfully(to, fullName);
        return ResponseEntity.ok("Password reset success email sent successfully");
    }

    @PostMapping("/send-transaction")
    public ResponseEntity<String> sendTransactionMail(@RequestParam String to,
                                                      @RequestParam String fullName,
                                                      @RequestParam String messageContent) {
        mailService.sendTransactionMail(to, fullName, messageContent);
        return ResponseEntity.ok("Transaction email sent successfully");
    }
}
