package com.microstore.dhanya.service;

import com.microstore.dhanya.DTO.LoginRequestDTO;
import com.microstore.dhanya.DTO.LoginResponseDTO;
import com.microstore.dhanya.DTO.RegisterRequestDTO;
import com.microstore.dhanya.DTO.RegisterResponseDTO;
import com.microstore.dhanya.Message.LoginMessage;
import com.microstore.dhanya.Message.RegistrationMessage;
import com.microstore.dhanya.model.Token;
import com.microstore.dhanya.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microstore.dhanya.repository.UserRepository;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    TokenService tokenService;

    // register a new user
    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) throws NoSuchAlgorithmException {
        // check if user with same email address already exists
        String email = registerRequestDTO.getEmail();
        if(repository.findUserByEmail(email) != null)
        {
            return new RegisterResponseDTO("failed", RegistrationMessage.USER_EXISTS);
        }

        // encrypt the password and store the user entry in DB
        String encryptedPassword = encryptPassword(registerRequestDTO.getPassword());
        User newUser = new User(registerRequestDTO.getFirstName(), registerRequestDTO.getLastName(),registerRequestDTO.getEmail(), encryptedPassword);
        repository.save(newUser);

        // generate a token for the user and store this token in DB
        //Token newToken = new Token(newUser);
        //tokenService.saveToken(newToken);

        return new RegisterResponseDTO("success", RegistrationMessage.USER_REGISTRATION_SUCCESS);

    }

    // login of user
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws NoSuchAlgorithmException {
        User user = repository.findUserByEmail(loginRequestDTO.getEmail());

        // no user with the sent email
        if(user == null)
        {
            return new LoginResponseDTO("failed", LoginMessage.LOGIN_EMAIL_FAILURE);
        }

        // check if password sent and the password of the found user in the DB matches
        // surround by TRY CATCH LATER
        String encryptedPassword = encryptPassword(loginRequestDTO.getPassword());

        if(encryptedPassword.compareTo(user.getPassword()) != 0)
        {
            return new LoginResponseDTO("failed", LoginMessage.LOGIN_PASSWORD_FAILURE);

        }

        // else
        // create / update the token in tokens table for the user and return the token
        Token token = tokenService.findUserToken(user);

        // if token is NULL - then this is the user's first login. Enter a new token record in DB
        if(token == null)
        {
            token = new Token(user);
        }

        // For every login generate fresh token values, i.e token value string and created time , and save/update in DB
        tokenService.generateToken(token);
        tokenService.saveToken(token);

        return new LoginResponseDTO("success",LoginMessage.LOGIN_SUCCESS,token.getToken());
    }


    private String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String encryptedPassword = DatatypeConverter
                .printHexBinary(digest).toUpperCase();

        return encryptedPassword;

    }




}
