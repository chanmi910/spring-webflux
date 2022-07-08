package com.example.springwebflux.controller;

import com.example.springwebflux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("")
public class IndexController {

    @Autowired
    UserService userService;

    @GetMapping("/index")
    public Mono<String> findAll(Model model) {
        model.addAttribute("data", "WebFlux!!!");
        model.addAttribute("userList", userService.getAllUsers());
        return Mono.just("index");
    }
}
