package com.example.RentCar.Controllers;


import com.example.RentCar.DTOS.UserLoginResponseDTO;
import com.example.RentCar.DTOS.UserRequestDTO;
import com.example.RentCar.DTOS.UserResponseDTO;
import com.example.RentCar.Exceptions.UserException;
import com.example.RentCar.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {


    @Autowired
    private UserServices userService;
    private AuthController(UserServices userServices){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signup(@RequestBody @Valid UserRequestDTO userRequestDtos) throws IOException {



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
    public ResponseEntity<UserLoginResponseDTO> login(@RequestParam("userEmail") String userEmail, @RequestParam("userPassword") String userPassword) throws IOException {
        try {
            System.out.println(userEmail);
            String response = userService.login(userEmail, userPassword);

            if (response != null) {
                // User is found

                return ResponseEntity.ok(new UserLoginResponseDTO("Logged in Successful", "success",response));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new UserLoginResponseDTO("Internal server error", "error",null));
            }
        } catch (UserException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserLoginResponseDTO(e.getMessage(),"error",null));
        }

    }

}
