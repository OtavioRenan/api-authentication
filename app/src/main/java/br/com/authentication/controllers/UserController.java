package br.com.authentication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.authentication.domain.dtos.UserRequestDTO;
import br.com.authentication.domain.ports.interfaces.UserServicePort;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServicePort service;

    @PostMapping
    public void create(@RequestBody UserRequestDTO u) {
        service.create(u);
    }
}
