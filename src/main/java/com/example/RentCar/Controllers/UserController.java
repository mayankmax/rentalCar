package com.example.RentCar.Controllers;


import com.example.RentCar.DTOS.UserAddressRequestDTO;
import com.example.RentCar.DTOS.UserAddressResponseDTO;
import com.example.RentCar.Services.AddressService;
import com.example.RentCar.Services.JwtServicesImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

//    @Autowired
//    private final JwtServicesImpl jwtServices;
//    private final AddressService addressService;
//
//    public UserController(JwtServicesImpl jwtServices, AddressService addressService) {
//        this.jwtServices = jwtServices;
//        this.addressService = addressService;
//    }
    @PostMapping("/modifyaddress")
    //@PreAuthorize("hasRole('User')")
    public ResponseEntity<UserAddressResponseDTO> ModifyAddress(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token, @RequestBody @Valid UserAddressRequestDTO userAddressRequestDTO){
        JwtServicesImpl jwtServices = new JwtServicesImpl();
        AddressService addressService = new AddressService();
        try {
            String userEmail = jwtServices.extractUserName(token);
            System.out.println("user from controller" + userEmail);
            UserAddressResponseDTO userAddressResponseDTO = addressService.UserAddressModify(userAddressRequestDTO,userEmail);
            if(userAddressResponseDTO != null)
            {
                return ResponseEntity.ok(userAddressResponseDTO);
            }
        }catch(Exception e) {

            e.printStackTrace();

        }

        return null;

    }

}
