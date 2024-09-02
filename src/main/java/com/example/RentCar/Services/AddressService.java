package com.example.RentCar.Services;

import com.example.RentCar.DTOS.UserAddressRequestDTO;
import com.example.RentCar.DTOS.UserAddressResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    public static UserAddressResponseDTO UserAddressModify(UserAddressRequestDTO userAddressRequestDTO, String userEmail) {
        String houseno = userAddressRequestDTO.getHouseNo();
         String landmark = userAddressRequestDTO.getLandmark();
         String city = userAddressRequestDTO.getCity();
         String district = userAddressRequestDTO.getDistrict();
         String state = userAddressRequestDTO.getState();
         int pincode = userAddressRequestDTO.getPincode();


        System.out.println("user" + userEmail);

        return null;


    }
}
