package com.usermanagement.service;

import com.usermanagement.model.ActivityLog;
import com.usermanagement.repository.ActivityLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActivityLogService {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    public ActivityLog logActivity(Long userId, String action, String details) {
        ActivityLog log = new ActivityLog(userId, action, details);
        return activityLogRepository.save(log);
    }

    public ActivityLog logActivity(Long userId, String action, String details, String ipAddress) {
        ActivityLog log = new ActivityLog(userId, action, details, ipAddress);
        return activityLogRepository.save(log);
    }

    public List<ActivityLog> getUserActivities(Long userId) {
        return activityLogRepository.findByUserIdOrderByTimestampDesc(userId);
    }

    public List<ActivityLog> getRecentActivities() {
        return activityLogRepository.findTop10ByOrderByTimestampDesc();
    }
}