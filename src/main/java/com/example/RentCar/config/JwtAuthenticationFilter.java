package com.example.RentCar.config;

import com.example.RentCar.DTOS.UserRequestDTO;
import com.example.RentCar.Models.User;
import com.example.RentCar.Repository.UserRepository;
import com.example.RentCar.Services.JwtServices;
import com.example.RentCar.Services.JwtServicesImpl;
import com.example.RentCar.Services.UserServices;
import com.example.RentCar.Services.UserServicesImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserServicesImpl userServicesimpl;
    private final UserRepository userRepository;
   // private final UserRequestDTO userRequestDTO;
    private final JwtServicesImpl jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        //System.out.println("authheader" + authHeader);
         //JwtServicesImpl jwtService = new JwtServicesImpl();
        final String userEmail;
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.split(" ")[1].trim();
        System.out.println("jwt" + jwt);
        userEmail = jwtService.extractUserName(jwt);
        //System.out.println("username" + userEmail);
        if (StringUtils.isNotEmpty(userEmail)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userRepository.findByuserEmail(userEmail);
            //System.out.println(user);
            if (jwtService.isTokenValid(jwt, userServicesimpl)) {
                //System.out.println("I am in");
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userServicesimpl, null, user.getAuthorities());
                System.out.println(authToken);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }
}
