package com.accelerator.services;

import com.accelerator.dto.User;

public class AuthenticationService
{
    public User chekUser(String email, String password)
    {
        UserService userService = new UserService();
        return userService.receiveUser(email, password);
    }
}
