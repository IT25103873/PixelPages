package com.PixelPages.BookStore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAdminCredentials(String toEmail, String adminId, String tempPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("BookStore Admin Account Created");
        message.setText(
                "Hello,\n\n" +
                        "Your admin account has been created.\n\n" +
                        "Admin ID: " + adminId + "\n" +
                        "Temporary Password: " + tempPassword + "\n\n" +
                        "This password is valid for 30 minutes.\n" +
                        "Please log in and change your password immediately.\n\n" +
                        "Regards,\nBookStore Team"
        );
        mailSender.send(message);
    }
}