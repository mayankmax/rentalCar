package com.example.RentCar.Services;


import com.example.RentCar.Constraints.ProfileConstraints;
import com.example.RentCar.DTOS.UserRequestDTO;
import com.example.RentCar.DTOS.UserResponseDTO;
import com.example.RentCar.Exceptions.UserException;
import com.example.RentCar.Models.User;
import com.example.RentCar.Models.enums.Role;
import com.example.RentCar.Repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private final JwtServicesImpl jwtService;

    public UserServices(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtServicesImpl jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponseDTO signup(UserRequestDTO userRequestDtos) throws UserException {



        String name = userRequestDtos.getUserName();
        String email = userRequestDtos.getUserEmail();
        String password = userRequestDtos.getPassword();



//        String confirmPassword = userRequestDtos.getConfirmPassword();

        ProfileConstraints profileConstraints = new ProfileConstraints();

        try {
            if (userRepository.findByuserEmail(email) != null) {
                throw new UserException.UserAlreadyExist("Email already exists, please try to login or create account with new email");
            }

            String emailValidationResult = profileConstraints.isValidEmail(email);
            if (!"true".equals(emailValidationResult)) {
                throw new UserException.InvalidEmailException(emailValidationResult);
            }

            // Validate name
            String nameValidationResult = profileConstraints.isValidName(name);
            if (!"true".equals(nameValidationResult)) {
                throw new UserException.InvalidNameException(nameValidationResult);
            }

            // Validate phone
//        String phoneValidationResult = profileConstraints.isValidIndianPhoneNumber(phone);
//        if (!"true".equals(phoneValidationResult)) {
//            throw new UserException.InvalidPhoneException(phoneValidationResult);
//        }

            // Validate password
            String passwordValidationResult = profileConstraints.isValidPassword(password);
            if (!"true".equals(passwordValidationResult)) {
                throw new UserException.InvalidPasswordException(passwordValidationResult);
            }

            Role role = Role.valueOf("User");


           // password = passwordEncoder.encode(userRequestDtos.getPassword());



//            var user = (UserDetails) User.builder().userName(name)
//                    .userEmail(email).password(passwordEncoder.encode(userRequestDtos.getPassword()))
//                    .role(Role.User).build();

            User user = new User(name, email, password,role);

            System.out.println(user);


            userRepository.save(user);
            String jwt  = jwtService.generateToken(user);
            System.out.println(jwt);

            UserResponseDTO userResponseDTO = new UserResponseDTO<>("User created successfully", "success", user,jwt);

            return userResponseDTO;
        } catch (UserException.UserAlreadyExist e) {
            throw e;
        } catch (UserException e) {
            throw e;
        } catch (Exception e) {
            // Log the exception
            //  logger.error("Exception occurred while signing up user: ", e);

            // Throw a generic exception
            throw new UserException("An unexpected error occurred while signing up. Please try again later.");
        }
    }


    public User login(String email, String password)throws UserException {


        try {



            if (userRepository.findByuserEmail(email) == null) {
                throw new UserException.InvalidEmailException("Email is not present in DB, please sign up to continue");
            }



            // Check if the provided password matches the stored password
            if (!userRepository.findByuserEmail(email).getPassword().equals(password)) {
                throw new UserException.InvalidPasswordException("Incorrect password");
            }

            return userRepository.findByuserEmail(email);
        } catch (UserException.InvalidEmailException e) {
            throw e;
        } catch (UserException.InvalidPasswordException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserException("An unexpected error occurred while logging in. Please try again later.");
        }
    }

    public User userDetailsService(String email) {
        return userRepository.findByuserEmail(email);
    }


}
