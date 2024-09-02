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
public class UserServices {


    @Autowired
    private final UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    @Autowired
    private final PasswordEncoder passwordEncoder;



    public UserServices(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        //this.jwtService = jwtService;
    }

    public UserResponseDTO signup(UserRequestDTO userRequestDtos) throws UserException {

         JwtServicesImpl jwtServices = new JwtServicesImpl();


        String name = userRequestDtos.getUserName();
        String email = userRequestDtos.getUserEmail();
        String password = userRequestDtos.getPassword();

        System.out.println("name"+name + "email" + email + "password" + password);

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


            User user = new User(name, email, passwordEncoder.encode(password),role);
            userRepository.save(user);

            String jwt  = jwtServices.generateToken(user); //The JwtService is invoked to generate a JWT for the User object.
            //System.out.println(jwt);

            UserResponseDTO userResponseDTO = new UserResponseDTO<>("User created successfully", "success", user,jwt);

            return userResponseDTO;
        } catch (UserException.UserAlreadyExist e) {
            throw e;
        } catch (UserException e) {
            throw e;
        }catch(NullPointerException e){
            System.out.println(e);
            e.printStackTrace();
            throw e;

        }
        catch (Exception e) {
            // Log the exception
            //  logger.error("Exception occurred while signing up user: ", e);

            // Throw a generic exception
            throw new UserException("An unexpected error occurred while signing up. Please try again later.");
        }
    }


    public String login(String email, String password)throws UserException {
        //System.out.println(email + password);
        JwtServicesImpl jwtServices = new JwtServicesImpl();

        try {



            if (userRepository.findByuserEmail(email) == null) {
                throw new UserException.InvalidEmailException("Email is not present in DB, please sign up to continue");
            }



            // Check if the provided password matches the stored password
            if (passwordEncoder.matches(userRepository.findByuserEmail(email).getPassword(),password)) {
                throw new UserException.InvalidPasswordException("Incorrect password");
            }

            User user = userRepository.findByuserEmail(email);
            if(user != null){
               // System.out.println(user.getUserEmail());
                return  jwtServices.generateToken(user);
            }else{
                return "Login Failed";
            }
        } catch (UserException.InvalidEmailException e) {
            throw e;
        } catch (UserException.InvalidPasswordException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserException("An unexpected error occurred while logging in. Please try again later.");
        }
    }




}
