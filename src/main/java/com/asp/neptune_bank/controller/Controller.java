package com.asp.neptune_bank.controller;

import com.asp.neptune_bank.models.Users;
import com.asp.neptune_bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class Controller {

    @Autowired
    private UserService userService;


    @PostMapping("/user")
    public ResponseEntity<Users> createUser(@RequestBody Users users) {
        return new ResponseEntity<>(userService.createUser(users), HttpStatus.CREATED);
    }


}
