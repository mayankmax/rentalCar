package com.example.RentCar.Models;

import com.example.RentCar.Models.enums.ProfileStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserProfile extends BaseModel {

    private String userPhone;
    @OneToOne
    private UserAddress userAddress;
    @OneToOne
    private User user;
    @Enumerated
    private ProfileStatus profileStatus;
    private LocalDateTime createdAt;


}
