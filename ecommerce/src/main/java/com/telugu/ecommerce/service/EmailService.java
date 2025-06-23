package com.telugu.ecommerce.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendVerificationOtpEmail(String userEmail, String otp, String subject, String text) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

            try {
                mimeMessageHelper.setTo(userEmail);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            try {
                mimeMessageHelper.setSubject(subject);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            try {
                mimeMessageHelper.setText(text, true);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

            javaMailSender.send(mimeMessage);

        } catch (MailException  e) {
            e.printStackTrace();
            throw new MailSendException("Failed to send email: " + e.getMessage(), e);

        }
    }
}
