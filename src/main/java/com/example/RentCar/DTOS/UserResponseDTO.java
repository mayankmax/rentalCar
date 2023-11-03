package com.example.RentCar.DTOS;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO<T> {

    private String message;
    private String status;
    private T data;
    private String token;


    public UserResponseDTO() {
    }

    public UserResponseDTO(String message, String status, T data,String token) {
        this.message = message;
        this.status = status;
        this.data = data;
        this.token = token;
    }

}
