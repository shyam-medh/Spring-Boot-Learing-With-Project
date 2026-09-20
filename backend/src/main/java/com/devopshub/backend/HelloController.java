package com.devopshub.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @GetMapping("/")
    public String sayHello(){
        return "Welcome to DevOpsHub! The application is running successfully.";
    }
}
