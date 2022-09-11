package com.example.authupdatedemo.controllers;

import com.example.authupdatedemo.beans.LoginRequest;
import com.example.authupdatedemo.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/")
@RequiredArgsConstructor
public class MyController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        authenticationManager.authenticate(token);
        String jwt = jwtUtil.generate(loginRequest.getUsername());
        return ResponseEntity.ok(jwt);
    }

    @GetMapping("hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello");
    }

}
