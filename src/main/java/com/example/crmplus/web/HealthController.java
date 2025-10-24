package com.example.crmplus.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public String health() {
        return "OK";
    }
}