package com.microstore.dhanya.service;

import com.microstore.dhanya.constants.AuthenticationConstants;
import com.microstore.dhanya.model.Token;
import com.microstore.dhanya.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microstore.dhanya.repository.TokenRepository;

import java.time.Instant;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    TokenRepository repository;

    // to save the token

    public void saveToken(Token token)
    {

        repository.save(token);
    }

    // find the token by the user
    public Token findUserToken(User user)
    {
        return repository.findTokenByUser(user);
    }

    public void generateToken(Token token)
    {
        String tokenValue = UUID.randomUUID().toString();
        Long createdTime = Instant.now().getEpochSecond();

        token.setToken(tokenValue);
        token.setCreatedTime(createdTime);
    }

    public Integer authenticateToken(String tokenValue)
    {
        Token token = repository.findTokenByToken(tokenValue);

        if(token == null)       // token not present in DB , perhaps first login
        {
            return AuthenticationConstants.TOKEN_INVALID;
        }

        Long currTime = Instant.now().getEpochSecond();

        if(currTime - token.getCreatedTime() > AuthenticationConstants.TOKEN_VALIDITY_TIME_PERIOD)
        {
            return AuthenticationConstants.TOKEN_EXPIRED;
        }

        // token is Valid
        return AuthenticationConstants.TOKEN_VALID;
    }
}
