package com.microstore.dhanya.controller;

import com.microstore.dhanya.DTO.LoginRequestDTO;
import com.microstore.dhanya.DTO.LoginResponseDTO;
import com.microstore.dhanya.DTO.RegisterRequestDTO;
import com.microstore.dhanya.DTO.RegisterResponseDTO;
import com.microstore.dhanya.model.Token;
import com.microstore.dhanya.service.TokenService;
import com.microstore.dhanya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService service;

    @Autowired
    TokenService testService;

    // endpoint for signup
    @PostMapping("/register")
    public RegisterResponseDTO register(@RequestBody RegisterRequestDTO registerRequestDTO) throws NoSuchAlgorithmException {
        return service.register(registerRequestDTO);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) throws NoSuchAlgorithmException {
        return service.login(loginRequestDTO);
    }


    // trials to see if expiry of tokens works
    @GetMapping("/{token}")
    public ResponseEntity<String> getEmployeeById(@PathVariable(value="token") String token) throws ArithmeticException
    {
        Integer val = testService.authenticateToken(token);
        String valStr = Integer.toString(val);

        return ResponseEntity.ok().body(valStr);
    }

}