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
        message.setSubject("Welcome to Revgoogle – Registration Successful");

        message.setText(
            "Dear " + companyName + ",\n\n" +
            "Welcome to Revgoogle.\n\n" +
            "Thank you for registering on our platform.\n" +
            "We’re glad to have you onboard.\n\n" +

            "Revgoogle is built to help software companies execute work faster, more efficiently, " +
            "and without the operational friction of hiring or managing external resources.\n\n" +

            "What you can do on Revgoogle:\n" +
            "- Post development tasks with fixed scope and timelines\n" +
            "- Get work executed through a structured and quality-controlled process\n" +
            "- Review, approve, and pay securely\n" +
            "- Handle small but critical work without disrupting your internal teams\n\n" +

            "Our platform is designed to ensure clarity, control, and reliability at every step of execution.\n\n" +

            "Getting Started:\n" +
            "You can log in anytime and post your first task to experience how Revgoogle works in real execution scenarios.\n\n" +

            "If you need any assistance or have questions, our support team is always available to help you.\n\n" +

            "Email: support@revgoogle.com\n" +
            "Website: www.revgoogle.com\n\n" +

            "We look forward to supporting your execution needs.\n\n" +

            "Warm regards,\n" +
            "Team Revgoogle\n" +
            "A platform for work that moves business forward"
        );

        mailSender.send(message);
    }

    
    public void sendResetPasswordEmail(String toEmail, String resetLink) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("RevStream – Password Reset Request");

        message.setText(
            "Dear User,\n\n" +
            "We received a request to reset your RevStream account password.\n\n" +
            "Please click the secure link below to set a new password:\n\n" +
            resetLink + "\n\n" +
            "For your security, this link will expire in 15 minutes and can only be used once.\n\n" +
            "If you did not request this change, please ignore this email or contact our support team.\n\n" +
            "Best Regards,\n" +
            "RevStream Security Team"
        );


        mailSender.send(message);
    }

}
