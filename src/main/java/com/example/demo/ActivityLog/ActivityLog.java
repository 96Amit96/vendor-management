package com.example.demo.ActivityLog;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;  
    private String performedBy;  
    private String details;  
    private LocalDateTime timestamp = LocalDateTime.now();
}

