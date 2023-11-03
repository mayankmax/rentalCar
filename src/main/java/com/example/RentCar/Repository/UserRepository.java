package com.example.RentCar.Repository;

import com.example.RentCar.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    User findByuserEmail(String email);

}
