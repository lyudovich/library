package com.library.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SecurityUtil {

    public static void requireAdmin(HttpServletRequest request) {

        String role = (String) request.getAttribute("role");

        if (role == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        if (!"ADMIN".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin access required");
        }
    }
}
