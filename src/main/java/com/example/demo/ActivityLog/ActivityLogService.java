package com.example.demo.ActivityLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActivityLogService {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    public void logActivity(String action, String performedBy, String details) {
        System.out.println("Logging Activity: " + action + " by " + performedBy);
        ActivityLog log = new ActivityLog();
        log.setAction(action);
        log.setPerformedBy(performedBy);
        log.setDetails(details);
        activityLogRepository.save(log);
    }

    public List<ActivityLog> getAllLogs() {
        return activityLogRepository.findAll();
    }
}

