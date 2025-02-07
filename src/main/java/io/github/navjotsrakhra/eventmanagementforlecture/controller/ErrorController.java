package io.github.navjotsrakhra.eventmanagementforlecture.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/error-test")
public class ErrorController {
    @GetMapping
    public String error() {
        throw new RuntimeException();
    }
}
