package com.example.RentCar.Services;

import com.example.RentCar.Models.User;
import org.springframework.security.core.userdetails.UserDetails;


public interface JwtServices {
    String extractUserName(String token);
    String generateToken(User user);
    boolean isTokenValid(String token, UserServicesImpl userServices);
}
