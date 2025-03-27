package com.example.demo.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.ActivityLog.ActivityLogService;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "http://localhost:5173")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ActivityLogService activityLogService;

    @PostMapping("/send")
    public String sendEmail(@RequestParam String to,
            @RequestParam(required = false, defaultValue = "") String cc,
            @RequestParam String subject,
            @RequestParam String body) {
        emailService.sendEmail(to, cc, subject, body);
        return "Email Sent Successfully!";
    }

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(
            @RequestParam String to,
            @RequestParam(required = false) String cc,
            @RequestParam String subject,
            @RequestParam String body,
            @RequestParam(required = false) MultipartFile attachment) {
        try {
            emailService.sendEmailWithAttachment(to, cc, subject, body, attachment);
            activityLogService.logActivity("Email", "Admin", "Email Sent: " + to);
            return ResponseEntity.ok("✅ Email sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Failed to send email: " + e.getMessage());
        }
    }
}
