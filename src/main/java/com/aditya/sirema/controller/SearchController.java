package com.aditya.sirema.controller;

import com.aditya.sirema.dto.RequestDto;
import com.aditya.sirema.service.RequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/searchRequests")
public class SearchController {
    @Autowired
    private RequestServiceImpl requestService;

    @GetMapping
    public List<RequestDto> searchRequests(@RequestParam(required = false) String namaPengaju,
                                           @RequestParam(required = false) String bentukRequest,
                                           @RequestParam(required = false) String judulRequest) {
        return requestService.searchRequests(namaPengaju, bentukRequest, judulRequest);
    }
}
