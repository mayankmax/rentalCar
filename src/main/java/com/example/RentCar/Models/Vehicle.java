package com.example.RentCar.Models;

import com.example.RentCar.Models.enums.VehicleStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Vehicle extends BaseModel{
    private String vehicleName;
    private String manufactureName;
    private String seater;
    private String mileage;
    private Date manufacturingYear;
    @Enumerated
    private VehicleStatus vehicleStatus;
    public Vehicle(){

    }

}
