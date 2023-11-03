package com.example.RentCar.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class UserAddress extends BaseModel{
    private String HouseNo;
    private String landmark;
    private String city;
    private String district;
    private String state;
    private int pincode;
    @OneToOne
    private UserProfile userProfile;


    public UserAddress() {
    }
}
