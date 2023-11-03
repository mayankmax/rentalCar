package com.example.RentCar.Models;


import com.example.RentCar.Models.enums.StoreStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Store extends BaseModel {

    private String storeName;
    @OneToOne
    private StoreAddress storeAddress;
    @Enumerated
    private StoreStatus storeStatus;
    // do i need cars here?
    //yes
    @OneToMany
    private List<Vehicle> vehicleList;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;




}
