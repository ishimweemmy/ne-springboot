package com.ishimweemmy.templates.springboot.v1.standalone;

import com.ishimweemmy.templates.springboot.v1.exceptions.AppException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    @Value("${app.frontend.reset-password}")
    private String resetPasswordUrl;

    @Value("${app.frontend.support-email}")
    private String supportEmail;

    public void sendResetPasswordMail(String to, String fullName, String resetCode) {
        try {
            MimeMessage message = this.mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            Context context = new Context();
            context.setVariable("fullName", fullName);
            context.setVariable("resetCode", resetCode);
            context.setVariable("resetUrl", resetPasswordUrl);
            context.setVariable("supportEmail", supportEmail);
            context.setVariable("currentYear", LocalDate.now().getYear());

            String htmlContent = templateEngine.process("forgot-password-email", context);

            helper.setTo(to);
            helper.setSubject("Password Reset Request");
            helper.setText(htmlContent, true);

            this.mailSender.send(message);
        } catch (MessagingException e) {
            throw new AppException("Error sending email", e);
        }
    }

    public void sendActivateAccountEmail(String to, String fullName, String verificationCode) {
        try {
            MimeMessage message = this.mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            Context context = new Context();
            context.setVariable("fullName", fullName);
            context.setVariable("verificationCode", verificationCode);
            context.setVariable("resetUrl", resetPasswordUrl);
            context.setVariable("supportEmail", supportEmail);
            context.setVariable("currentYear", LocalDate.now().getYear());

            String htmlContent = templateEngine.process("verify-account-email", context);

            helper.setTo(to);
            helper.setSubject("Account activation Request");
            helper.setText(htmlContent, true);

            this.mailSender.send(message);
        } catch (MessagingException e) {
            throw new AppException("Error sending email", e);
        }
    }

    public void sendAccountVerifiedSuccessfullyEmail(String to, String fullName) {
        try {
            MimeMessage message = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            Context context = new Context();
            context.setVariable("fullName", fullName);
            context.setVariable("supportEmail", supportEmail);
            context.setVariable("currentYear", LocalDate.now().getYear());

            String htmlContent = templateEngine.process("account-verification-successful", context);

            helper.setTo(to);
            helper.setSubject("Account Verification Successful");
            helper.setText(htmlContent, true);

            this.mailSender.send(message);
        } catch (MessagingException e) {
            throw new AppException("Error sending message", e);
        }
    }

    public void sendPasswordResetSuccessfully(String to, String fullName) {
        try {
            MimeMessage message = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            Context context = new Context();
            context.setVariable("fullName", fullName);
            context.setVariable("supportEmail", supportEmail);
            context.setVariable("currentYear", LocalDate.now().getYear());

            String htmlContent = templateEngine.process("password-reset-successful", context);

            helper.setTo(to);
            helper.setSubject("Account Rejected");
            helper.setText(htmlContent, true);

            this.mailSender.send(message);
        } catch (MessagingException e) {
            throw new AppException("Error sending message", e);
        }
    }

    // public void sendTransactionMail
    public void sendTransactionMail(String to, String fullName, String messageContent) {
        try {
            MimeMessage message = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            Context context = new Context();
            context.setVariable("fullName", fullName);
            context.setVariable("messageContent", messageContent);
            context.setVariable("supportEmail", supportEmail);
            context.setVariable("currentYear", LocalDate.now().getYear());

            String htmlContent = templateEngine.process("transaction-email", context);

            helper.setTo(to);
            helper.setSubject("Transaction Notification");
            helper.setText(htmlContent, true);

            this.mailSender.send(message);
        } catch (MessagingException e) {
            throw new AppException("Error sending transaction email", e);
        }
    }
}