package com.example.website.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendRegistrationEmail(String toEmail, String companyName) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Registration Successful");
        message.setText(
                "Hello " + companyName + ",\n\n" +
                "You have been successfully registered on our platform.\n" +
                "Thank you for joining us!\n\n" +
                "Best Regards,\nWebsite Team"
        );

        mailSender.send(message);
    }
}
