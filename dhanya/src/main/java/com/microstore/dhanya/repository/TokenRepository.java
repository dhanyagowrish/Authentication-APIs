package com.microstore.dhanya.repository;

import com.microstore.dhanya.model.Token;
import com.microstore.dhanya.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token,Integer> {

    Token findTokenByUser(User user);
    Token findTokenByToken(String Token);
}
