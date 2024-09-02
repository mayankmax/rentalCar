package com.example.RentCar.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressRequestDTO {

    private String HouseNo;
    private String landmark;
    private String city;
    private String district;
    private String state;
    private int pincode;

}
