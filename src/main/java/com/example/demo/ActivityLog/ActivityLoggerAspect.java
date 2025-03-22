package com.example.demo.ActivityLog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class ActivityLoggerAspect {

    @Autowired
    private ActivityLogService activityLogService;

    @AfterReturning("execution(* com.example.demo.vendor.service.VendorService.createVendor(..))")
    public void logVendorCreation(JoinPoint joinPoint) {
        System.out.println("Aspect Running: Logging Vendor Creation");
        Object[] args = joinPoint.getArgs();
        activityLogService.logActivity("Vendor Created", "Admin", "Created Vendor: " + Arrays.toString(args));
    }

    @AfterReturning("execution(* com.example.demo.vendor.service.VendorService.updateVendor(..))")
    public void logVendorUpdate(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        activityLogService.logActivity("Vendor Updated", "Admin", "Updated Vendor: " + Arrays.toString(args));
    }

    @AfterReturning("execution(* com.example.demo.vendor.service.VendorService.deleteVendor(..))")
    public void logVendorDeletion(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        activityLogService.logActivity("Vendor Deleted", "Admin", "Deleted Vendor ID: " + args[0]);
    }
}

