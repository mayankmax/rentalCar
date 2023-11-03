package com.example.RentCar.Models;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class BIll extends BaseModel{


    private int amount;
    private Date createdAt;

    public BIll(){

    }

}
