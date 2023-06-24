package com.aditya.sirema.controller;

import com.aditya.sirema.entity.User;
import com.aditya.sirema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes
public class HomeController {

    @GetMapping("/")
    public String homePage() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if (role.equals("ROLE_ADMIN")) {
            return "redirect:/staff/";
        } else if (role.equals("ROLE_USER")) {
            return "redirect:/user/";
        } else {
            return "redirect:/login"; // Handle other roles or unauthorized access
        }
    }

}
