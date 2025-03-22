package com.example.demo.ActivityLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors/activity-logs")
@CrossOrigin(origins = "http://localhost:5173")
public class ActivityLogController {

    @Autowired
    private ActivityLogService activityLogService;

    @GetMapping
    public List<ActivityLog> getAllLogs() {
        return activityLogService.getAllLogs();
    }
}

