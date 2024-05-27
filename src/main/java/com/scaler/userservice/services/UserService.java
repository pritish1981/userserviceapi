package com.scaler.userservice.services;

import com.scaler.userservice.models.Token;
import com.scaler.userservice.models.User;
import com.scaler.userservice.repos.TokenRepo;
import com.scaler.userservice.repos.UserRepo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepo userRepo;
    private TokenRepo tokenRepo;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserRepo userRepo,
                       TokenRepo tokenRepo){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
    }

    public User signUp(String name, String email, String password){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        return userRepo.save(user);
    }

    public Token login(String email, String password) {
        // 1. Verify if the User Exists
        Optional<User> user = userRepo.findByEmail(email);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User not found for email" + email);
        }

        // 2. Verify the password
        if(!bCryptPasswordEncoder.matches(password, user.get().getHashedPassword())) {
            throw new RuntimeException("Password not matching");
        }

        // 3. Generate Token
        Token token = generateToken(user.get());
        tokenRepo.save(token);

        return token;
    }

    private Token generateToken(User user) {
        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphanumeric(10));
        LocalDate currentDate = LocalDate.now();
        LocalDate thirtyDaysLater = currentDate.plusDays(30);
        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());
        token.setExpiryAt(expiryDate);
        return token;
    }

    public User validateToken(String tokenValue){
        Optional<Token> token = tokenRepo.findByValueAndExpiryAtGreaterThan(tokenValue, new Date());
        if(token.isEmpty()){
            throw new RuntimeException("Invalid Token");
        }
        return token.get().getUser();
    }

}
