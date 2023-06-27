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
@RequestMapping("/staff/")
public class StaffController {
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
    public String viewDashboardStaff(ModelMap model) {
        User user = userService.findUserByEmail(getLogedinUsername());
        model.put("totalAllRequest", requestService.countRequests());
        model.put("totalAllPendingRequest", requestService.countRequestsByStatus(Request.Status.Pending));
        model.put("totalAllDitolakRequest", requestService.countRequestsByStatus(Request.Status.Ditolak));
        model.put("totalAllDisetujuiRequest", requestService.countRequestsByStatus(Request.Status.Disetujui));
        model.put("user", user);

        // Adding the top 5 requests to the model
        List<RequestDto> top8Requests = requestService.findTop8RequestsOrderByCreatedAtDesc();
        model.addAttribute("top8Requests", top8Requests);
        return "staff/dashboard";
    }
}
