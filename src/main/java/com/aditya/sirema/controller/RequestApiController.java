package com.aditya.sirema.controller;

import com.aditya.sirema.dto.RequestDto;
import com.aditya.sirema.entity.Request;
import com.aditya.sirema.mapper.RequestMapper;
import com.aditya.sirema.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public RequestApiController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public List<RequestDto> getAllRequests() {
        // Fetch all requests from the database, assuming that getRequests() returns List<RequestDto>
        return requestService.getRequests();
    }

    @GetMapping("/user")
    public List<RequestDto> getRequestsByUserId(@RequestParam Long userId) {
        // Fetch requests for a specific user by userId
        return requestService.findRequestsByUserId(userId);
    }
}
