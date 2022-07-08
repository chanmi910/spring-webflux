package com.example.fluxdemo.web;

import com.example.fluxdemo.domain.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class TestController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/")
    public Mono<String> findAll(Model model) {
        model.addAttribute("data", "WebFlux!!!");
        model.addAttribute("userList", customerRepository.findAll());
        return Mono.just("index");
    }
}
