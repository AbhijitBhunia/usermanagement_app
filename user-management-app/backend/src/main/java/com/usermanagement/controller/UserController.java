package com.usermanagement.controller;

import com.usermanagement.dto.ApiResponse;
import com.usermanagement.model.ActivityLog;
import com.usermanagement.model.User;
import com.usermanagement.service.ActivityLogService;
import com.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityLogService activityLogService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Map<String, Object> userData = new HashMap<>();
            userData.put("id", user.getId());
            userData.put("email", user.getEmail());
            userData.put("firstName", user.getFirstName());
            userData.put("lastName", user.getLastName());
            userData.put("createdAt", user.getCreatedAt());

            return ResponseEntity.ok(new ApiResponse(true, "User found", userData));
        } else {
            return ResponseEntity.status(404)
                .body(new ApiResponse(false, "User not found"));
        }
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<ApiResponse> getUserActivities(@PathVariable Long id) {
        List<ActivityLog> activities = activityLogService.getUserActivities(id);
        return ResponseEntity.ok(new ApiResponse(true, "Activities retrieved", activities));
    }
}