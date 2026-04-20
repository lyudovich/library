package com.library.services;

import com.library.models.User;

public interface IUserService {
    User register(String username, String password);
    User login(String username, String password);
}