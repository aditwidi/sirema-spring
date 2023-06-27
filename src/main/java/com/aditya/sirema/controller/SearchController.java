package com.aditya.sirema.controller;

import com.aditya.sirema.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    @Autowired
    private RequestService requestService;


}
