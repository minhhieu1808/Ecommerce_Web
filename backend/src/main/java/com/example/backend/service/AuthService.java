package com.example.backend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.config.Config;
import com.example.backend.data.entity.UserEntity;
import com.example.backend.data.request.LoginRequest;
import com.example.backend.data.request.UserRequest;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;
    public UserEntity getUser(LoginRequest loginRequest){
        UserEntity user = userRepository.findUserByEmail(loginRequest.getEmail());
        return user;
    }

    public UserEntity createUser(UserRequest userRequest){
        UserEntity existed = userRepository.findUserByEmail(userRequest.getEmail());
        if(existed != null) return null;
        UserEntity userEntity = new UserEntity();
        userEntity.setAddress(userRequest.getAddress());
        userEntity.setAnswer(userRequest.getAddress());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPassword(userRequest.getPassword());
        userEntity.setPhone(userRequest.getPhone());
        userEntity.setName(userRequest.getName());
        userEntity.setQuestion(userRequest.getQuestion());
        userEntity.setRole(userRequest.getRole());
        userRepository.saveAndFlush(userEntity);
        return userEntity;
    }
    public UserEntity updateUser(int id,UserRequest userRequest){
        UserEntity userEntity = userRepository.findUserById(id);
        if(userEntity == null) return null;
        userEntity.setAddress(userRequest.getAddress());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPassword(userRequest.getPassword());
        userEntity.setPhone(userRequest.getPhone());
        userEntity.setName(userRequest.getName());
        userRepository.saveAndFlush(userEntity);
        return userEntity;
    }
    public String getToken(UserEntity user) {
        UserEntity userEntity = userRepository.findUserByEmail(user.getEmail());
        String jwtToken = JWT.create()
                .withIssuer("auth")
                .withSubject("sign in")
                .withClaim("id", userEntity.getId())
                .withClaim("email", userEntity.getEmail())
                .withClaim("phone_number", userEntity.getPhone())
                .withClaim("is_admin", userEntity.getRole())
                .withClaim("username", userEntity.getName())
                .withClaim("address", userEntity.getAddress())
                .withIssuedAt(new Date())
                .withJWTId(UUID.randomUUID()
                        .toString())
                .sign(Config.JWT_ALGORITHM);
        return jwtToken;
    }
    public boolean checkTokenUser(String token) {
        DecodedJWT jwt = Config.JWT_VERIFIER.verify(token);
        Claim userClaim = jwt.getClaim("id");
        UserEntity user= userRepository.findUserByIdAndRole(userClaim.asInt(),0);
        if(user == null) return false;
        return true;
    }
    public boolean checkTokenAdmin(String token) {
        DecodedJWT jwt = Config.JWT_VERIFIER.verify(token);
        Claim userClaim = jwt.getClaim("id");
        UserEntity user= userRepository.findUserByIdAndRole(userClaim.asInt(),1);
        if(user == null) return false;
        return true;
    }

    public boolean checkToken(String token) {
        DecodedJWT jwt = Config.JWT_VERIFIER.verify(token);
        Claim userClaim = jwt.getClaim("id");
        UserEntity user= userRepository.findUserById(userClaim.asInt());
        if(user == null) return false;
        return true;
    }
}
