package com.library.controllers;

import com.library.models.User;
import com.library.repositories.UserRepository;
import com.library.security.PasswordUtil;
import com.library.services.UserService;
import com.library.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public User register(@RequestBody Map<String, String> body) {
        return userService.register(
                body.get("username"),
                body.get("password")
        );
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body,
                                     HttpServletResponse response) {

        String username = body.get("username");
        String password = body.get("password");

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid credentials"
                ));

        if (!PasswordUtil.matches(password, user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid credentials"
            );
        }

        String token = JwtUtil.generate(user.getUsername(), user.getRole());

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60); // 1 година
        // cookie.setSecure(true); //  HTTPS

        response.addCookie(cookie);

        return Map.of(
                "username", user.getUsername(),
                "role", user.getRole()
        );
    }
}
