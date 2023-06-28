package com.aditya.sirema.controller;

import com.aditya.sirema.dto.NotificationDto;
import com.aditya.sirema.dto.RequestDto;
import com.aditya.sirema.dto.UserDto;
import com.aditya.sirema.entity.Request;
import com.aditya.sirema.entity.User;
import com.aditya.sirema.service.NotificationService;
import com.aditya.sirema.service.RequestService;
import com.aditya.sirema.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@SessionAttributes("name")
public class RequestsController {
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

// CONTROL FOR ROLE_USER
    @GetMapping("/user/requests")
    public String viewTabelRequest(Model model) {
        User user = userService.findUserByEmail(getLogedinUsername());
        if (user != null) {
            List<RequestDto> requests = requestService.findRequestsByUserId(user.getId());
            model.addAttribute("user", user);
            model.addAttribute("requests", requests);
        }
        return "user/requests";
    }

    @GetMapping("/user/requests/add-request")
    public String viewFormAddRequest(Model model) {
        User user = userService.findUserByEmail(getLogedinUsername());
        model.addAttribute("user", user);
        model.addAttribute("request", new RequestDto());
        return "user/add-request";
    }

    @PostMapping("/user/add-request/submit")
    public RedirectView simpanRequest(Model model, @ModelAttribute("request") RequestDto requestDto) {
        model.addAttribute("request", requestDto);
        NotificationDto notificationDto = new NotificationDto();

        User user = userService.findUserByEmail(getLogedinUsername());
        if (user != null) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            requestDto.setUser(userDto);
            // Create a notification for the save request
            notificationDto.setMessage("New request created");
            notificationDto.setUser(userDto);
            notificationService.createNotification(notificationDto);
        }

        requestService.saveRequest(requestDto);
        return new RedirectView("/user/requests");
    }

    @GetMapping("/user/requests/delete/{requestId}")
    public String deleteRequest(@PathVariable Long requestId) {
        requestService.deleteRequest(requestId);
        User user = userService.findUserByEmail(getLogedinUsername());
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        // Create a notification for the delete request
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setMessage("Request deleted");
        notificationDto.setUser(userDto);
        notificationService.createNotification(notificationDto);

        return "redirect:/user/requests";
    }

    @GetMapping("/user/requests/edit-request/{requestId}")
    public String viewFormEditRequest(Model model, @PathVariable Long requestId) {
        User user = userService.findUserByEmail(getLogedinUsername());
        RequestDto request = requestService.getRequest(requestId);
        model.addAttribute("user", user);
        model.addAttribute("request", request);
        return "user/edit-request";
    }

    @PostMapping("/user/edit-request/submit")
    public String editRequest(@ModelAttribute RequestDto requestDto, RedirectAttributes redirectAttributes) {
        User user = userService.findUserByEmail(getLogedinUsername());
        NotificationDto notificationDto = new NotificationDto();
        if (user != null) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            requestDto.setUser(userDto);
            // Create a notification for the update request
            notificationDto.setMessage("Request updated");
            notificationDto.setUser(userDto);
            notificationService.createNotification(notificationDto);
        }

        requestService.updateRequest(requestDto);
        redirectAttributes.addAttribute("message", "Request updated successfully");
        return "redirect:/user/requests";
    }

    // CONTROL FOR ROLE_ADMIN
    @GetMapping("/staff/requests")
    public String viewTabelRequestStaff(Model model) {
        User user = userService.findUserByEmail(getLogedinUsername());
            List<RequestDto> requests = requestService.getRequests();
            model.addAttribute("user", user);
            model.addAttribute("requests", requests);
        return "staff/requests";
    }

    @GetMapping("/staff/requests/view-request/{requestId}")
    public String viewFormDetailRequest(Model model, @PathVariable Long requestId) {
        User user = userService.findUserByEmail(getLogedinUsername());
        RequestDto request = requestService.getRequest(requestId);
        model.addAttribute("user", user);
        model.addAttribute("request", request);
        return "staff/view-request";
    }

    @PostMapping("/staff/view-request/submit")
    public String submitViewRequest(@ModelAttribute RequestDto requestDto, @RequestParam("statusValue") String statusValue,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors, such as returning to the form with error messages
            return "staff/requests";
        }

        String username = getLogedinUsername();
        User user = userService.findUserByEmail(username);

        if (user == null) {
            // Handle case where user is null, maybe redirect with an error message
            redirectAttributes.addAttribute("error", "User not found");
            return "redirect:/staff/requests";
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        requestDto.setUser(userDto);

        Request.Status requestStatus = convertToRequestStatus(statusValue);
        if (requestStatus == null) {
            // Handle invalid status value
            String errorMessage = "Invalid status value: " + statusValue;
            // You can log the error or set a custom error message in the redirect attributes
            redirectAttributes.addAttribute("error", errorMessage);
            return "redirect:/staff/requests";  // Redirect to an error page or the original form
        }

        requestDto.setStatus(requestStatus);  // Set the enum value in the RequestDto

        // Proceed only if user and status are properly set.
        if (requestDto.getUser() != null && requestDto.getStatus() != null) {
            requestService.updateRequest(requestDto);
            redirectAttributes.addAttribute("message", "Request updated successfully");
            return "redirect:/staff/requests";
        } else {
            // Handle case where user or status is not set
            redirectAttributes.addAttribute("error", "User or status not set");
            return "redirect:/staff/requests";
        }
    }
    private Request.Status convertToRequestStatus(String statusValue) {
        try {
            return Request.Status.valueOf(statusValue);  // Convert the string value to enum
        } catch (IllegalArgumentException e) {
            return null;  // Return null if the status value is invalid
        }
    }

}
