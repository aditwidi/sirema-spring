package com.aditya.sirema.controller;

import com.aditya.sirema.dto.NotificationDto;
import com.aditya.sirema.dto.RequestDto;
import com.aditya.sirema.entity.Request;
import com.aditya.sirema.entity.User;
import com.aditya.sirema.service.NotificationService;
import com.aditya.sirema.service.RequestService;
import com.aditya.sirema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes("name")
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private NotificationService notificationService;

    private String getLogedinUsername() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping("/")
    public String viewDashboardUser(ModelMap model) {
        User user = userService.findUserByEmail(getLogedinUsername());
        model.put("totalRequest", requestService.countTotalRequestByUser(user.getId()));
        model.put("totalPendingUserRequest", requestService.countTotalRequestByUserAndStatus(user.getId(), Request.Status.Pending));
        model.put("totalDitolakUserRequest", requestService.countTotalRequestByUserAndStatus(user.getId(), Request.Status.Ditolak));
        model.put("totalDisetujuiUserRequest", requestService.countTotalRequestByUserAndStatus(user.getId(), Request.Status.Disetujui));
        model.put("user", user);

        // Adding the top 5 requests to the model
        List<RequestDto> top5Requests = requestService.findTop5RequestsByUser(user.getId());
        model.addAttribute("top5Requests", top5Requests);

        // Adding the notifications for the logged-in user to the model
        List<NotificationDto> userNotifications = notificationService.findTop4NotificationsByUserId(user.getId());
        model.addAttribute("userNotifications", userNotifications);
        return "user/dashboard";
    }
}
