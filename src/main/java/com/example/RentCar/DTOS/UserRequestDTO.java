package com.example.RentCar.DTOS;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserRequestDTO {

    private String userName;
    private String userEmail;
    private String password;


}
