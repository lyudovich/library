package com.library.core.services;

import com.library.core.models.User;

public interface IUserService {
    User register(String username, String password);
    User login(String username, String password);
}
