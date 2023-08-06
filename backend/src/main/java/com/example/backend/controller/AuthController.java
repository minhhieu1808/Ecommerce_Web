package com.example.backend.controller;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.config.Config;
import com.example.backend.data.entity.UserEntity;
import com.example.backend.data.response.base.MyResponse;
import com.example.backend.data.request.LoginRequest;
import com.example.backend.data.request.UserRequest;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;
    @RequestMapping(value = "/api/v1/auth/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UserEntity user =authService.getUser(loginRequest);
        if(user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            MyResponse response = MyResponse
                    .builder()
                    .buildCode(200)
                    .buildMessage("Sai email hoặc mật khẩu")
                    .buildData(null)
                    .buildSuccess(false)
                    .get();
            return ResponseEntity.ok(response);
        }
        String jwt = authService.getToken(user);
        Map<String, String> mapReturn = new HashMap<>();
        mapReturn.put("token", jwt);
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildData(user)
                .buildSuccess(true)
                .buildToken(jwt)
                .get();
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/api/v1/auth/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody UserRequest userRequest) {
        UserEntity user = authService.createUser(userRequest);
        if(user == null) {
            MyResponse response = MyResponse
                    .builder()
                    .buildCode(200)
                    .buildMessage("Email đã tồn tại")
                    .buildData(user)
                    .buildSuccess(false)
                    .get();
            return ResponseEntity.ok(response);
        }
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildData(user)
                .buildSuccess(true)
                .get();
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/v1/auth/user-auth", method = RequestMethod.GET)
    public ResponseEntity<?> authUser(@RequestHeader(value="Authorization") String token) {
        boolean checkUser = authService.checkTokenUser(token);
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildSuccess(checkUser)
                .buildToken(token)
                .get();
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/api/v1/auth/admin-auth", method = RequestMethod.GET)
    public ResponseEntity<?> authAdmin(@RequestHeader(value="Authorization") String token) {
        boolean checkAdmin = authService.checkTokenAdmin(token);
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildSuccess(checkAdmin)
                .buildToken(token)
                .get();
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/api/v1/auth/profile", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProfile(@RequestHeader(value="Authorization") String token, @RequestBody UserRequest userRequest) {
        boolean checkUser = authService.checkToken(token);
        DecodedJWT jwt = Config.JWT_VERIFIER.verify(token);
        Claim userClaim = jwt.getClaim("id");
        UserEntity userEntity = authService.updateUser(userClaim.asInt(), userRequest);
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildSuccess(checkUser)
                .buildData(userEntity)
                .get();
        return ResponseEntity.ok(response);
    }
}
