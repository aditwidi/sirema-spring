package com.aditya.sirema.controller;

import com.aditya.sirema.dto.RequestDto;
import com.aditya.sirema.entity.Request;
import com.aditya.sirema.entity.User;
import com.aditya.sirema.mapper.RequestMapper;
import com.aditya.sirema.service.RequestService;
import com.aditya.sirema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/requests")
public class RequestApiController {
    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @Autowired
    public RequestApiController(RequestService requestService) {
        this.requestService = requestService;
    }

    private Long getLoggedInUserId() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.findUserByEmail(email);
        return user.getId();
    }

    @GetMapping
    public List<RequestDto> getAllRequests() {
        // Fetch all requests from the database, assuming that getRequests() returns List<RequestDto>
        return requestService.getRequests();
    }

    @GetMapping("user")
    public List<RequestDto> getRequestsByUserId(@RequestParam Long userId) {
        // Fetch requests for a specific user by userId
        return requestService.findRequestsByUserId(userId);
    }

    @GetMapping("staff/search")
    public List<RequestDto> searchRequests(@RequestParam(required = false) String namaPengaju,
                                           @RequestParam(required = false) String bentukRequest,
                                           @RequestParam(required = false) String judulRequest) {
        return requestService.searchRequests(namaPengaju, bentukRequest, judulRequest);
    }

    @GetMapping("user/search")
    public List<RequestDto> searchRequestsUser(@RequestParam(required = false) String namaPengaju,
                                               @RequestParam(required = false) String bentukRequest,
                                               @RequestParam(required = false) String judulRequest) {
        Long loggedInUserId = getLoggedInUserId();
        return requestService.searchRequestsUser(namaPengaju, bentukRequest, judulRequest, loggedInUserId);
    }
}
