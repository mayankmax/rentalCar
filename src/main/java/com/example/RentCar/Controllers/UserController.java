package com.example.RentCar.Controllers;


import com.example.RentCar.DTOS.UserRequestDTO;
import com.example.RentCar.DTOS.UserResponseDTO;
import com.example.RentCar.Exceptions.UserException;
import com.example.RentCar.Models.User;
import com.example.RentCar.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth/")
public class UserController {


    @Autowired
    private UserServices userService;
    private UserController(UserServices userServices){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signup(@RequestBody @Valid UserRequestDTO userRequestDtos) throws IOException {

        System.out.println(userRequestDtos.getUserName());

        try {
            UserResponseDTO userResponseDTO = userService.signup(userRequestDtos);
            return ResponseEntity.ok(userResponseDTO);
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UserResponseDTO<>(e.getMessage(), "error", null,null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserResponseDTO<>("Internal server error", "error", null,null));
        }
    }


    @GetMapping("/login")
    public ResponseEntity<UserResponseDTO<User>> login(@RequestParam("userEmail") String userEmail, @RequestParam("userPassword") String userPassword) throws IOException {
        try {
            User user = userService.login(userEmail, userPassword);

            if (user != null) {
                // User is found
                return ResponseEntity.ok(new UserResponseDTO<>("Logged in Successful", "success", user,"token"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new UserResponseDTO<>("Internal server error", "error", null,null));
            }
        } catch (UserException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserResponseDTO<>(e.getMessage(),"error",null,null));
        }

    }

}
