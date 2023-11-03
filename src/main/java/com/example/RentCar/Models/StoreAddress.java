package com.example.RentCar.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class StoreAddress extends BaseModel{

    private String plotNo;
    private String landmark;
    private String city;
    private String district;
    private String State;
    private int pincode;

    @OneToOne
    private Store store;

    public StoreAddress() {
    }
}
