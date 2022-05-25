package com.microstore.dhanya.controller;

import com.microstore.dhanya.DTO.LoginRequestDTO;
import com.microstore.dhanya.DTO.LoginResponseDTO;
import com.microstore.dhanya.DTO.RegisterRequestDTO;
import com.microstore.dhanya.DTO.RegisterResponseDTO;
import com.microstore.dhanya.model.Token;
import com.microstore.dhanya.service.TokenService;
import com.microstore.dhanya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService service;

    @Autowired
    TokenService tokenService;

    // endpoint for signup
    @PostMapping("/register")
    public RegisterResponseDTO register(@RequestBody RegisterRequestDTO registerRequestDTO) throws NoSuchAlgorithmException {
        return service.register(registerRequestDTO);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) throws NoSuchAlgorithmException {
        return service.login(loginRequestDTO);
    }


    // Test endpoint to see if authentication of tokens works
    @GetMapping("/")
    public ResponseEntity<String> authenticate(@RequestBody String token) throws ArithmeticException
    {
        Integer val = tokenService.authenticateToken(token);

        if(val == 1)
        {
            return new ResponseEntity<>("Token authenticated successfully", HttpStatus.OK);
        }

        else if(val == 2)
        {
            return new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
        }

        else
        {
            return new ResponseEntity<>("Expired Token", HttpStatus.UNAUTHORIZED);
        }
    }

}
