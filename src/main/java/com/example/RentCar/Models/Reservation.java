package com.example.RentCar.Models;

import com.example.RentCar.Models.enums.ReservationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity

public class Reservation extends BaseModel {

    @NotNull(message = "User is required field")
    @OneToOne
    private User user;
    @OneToOne
    private Vehicle vehicle;
    @Enumerated
    private ReservationStatus reservationStatus;
    private LocalDateTime reservationStateTime;
    private LocalDateTime reservationEndTime;
    private LocalDateTime cancelTime;

    public Reservation() {
    }
}
