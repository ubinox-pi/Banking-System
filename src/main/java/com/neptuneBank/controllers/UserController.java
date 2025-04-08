package com.neptuneBank.controllers;

import com.neptuneBank.DTOs.usersDTO.UsersDTO;
import com.neptuneBank.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UsersDTO> registerUser(@RequestBody @Valid UsersDTO registrationRequest) {
        UsersDTO usersDTO = userService.registerUser(registrationRequest);
        return usersDTO != null ? new ResponseEntity<>(usersDTO, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

}
