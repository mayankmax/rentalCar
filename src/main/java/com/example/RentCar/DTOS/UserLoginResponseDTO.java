package com.example.RentCar.DTOS;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponseDTO {
    private String message;
    private String status;

    private String token;


    public UserLoginResponseDTO() {
    }

    public UserLoginResponseDTO(String message, String status, String token) {
        this.message = message;
        this.status = status;

        this.token = token;
    }
}
