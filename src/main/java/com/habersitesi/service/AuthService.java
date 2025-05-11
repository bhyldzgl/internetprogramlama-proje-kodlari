package com.habersitesi.service;

import com.habersitesi.dto.LoginRequest;
import com.habersitesi.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);
    String login(LoginRequest request);
}
