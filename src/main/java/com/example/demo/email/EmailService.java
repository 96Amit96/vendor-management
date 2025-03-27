package com.example.demo.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String cc, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to.split(","));

        if (!cc.isEmpty()) {
            message.setCc(cc.split(","));
        }

        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendEmailWithAttachment(String to, String cc, String subject, String body, MultipartFile attachment)
            throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to.split(",")); // Support multiple recipients
        if (cc != null && !cc.isEmpty()) {
            helper.setCc(cc.split(","));
        }
        helper.setSubject(subject);
        helper.setText(body);

        if (attachment != null && !attachment.isEmpty()) {
            InputStreamSource source = new ByteArrayResource(attachment.getBytes());
            helper.addAttachment(attachment.getOriginalFilename(), source);
        }

        mailSender.send(message);
    }

}
