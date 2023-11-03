package com.example.RentCar.Constraints;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileConstraints {

    public String isValidEmail(String email) {
        if (email.isEmpty()) {
            return "Email cannot be empty";
        }

        // Regular expression for a valid email address
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            return "Invalid email format";
        }

        return "true"; // This indicates a valid email
    }

//    public String isValidIndianPhoneNumber(String phone){
//        if(phone.isBlank() || phone.isEmpty()) {
//            return "Phone is a required field";
//        }
//
//        // Regular expression for a valid Indian phone number
//        String regex = "^[6-9]\\d{9}$";
//        if (!phone.matches(regex)) {
//            return "Invalid Indian phone number format";
//        }
//
//        return "true";
//    }

    public String isValidName(String name) {
        if (name.isEmpty()) {
            return "Name cannot be empty";
        }

        // Regular expression for a valid name (only letters and spaces allowed)
        String regex = "^[A-Za-z\\s]+$";
        if (!name.matches(regex)) {
            return "Invalid name format";
        }

        return "true"; // This indicates a valid name
    }

    public String isValidPassword(String password) {
        if (password.isEmpty()) {
            return "Password cannot be empty";
        }

        // Check length (8 to 20 characters)
        if (password.length() < 8 || password.length() > 20) {
            return "Password must be between 8 and 20 characters";
        }

        // Check for at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return "Password must contain at least one uppercase letter";
        }

        // Check for at least one special character
        if (!password.matches(".*[!@#$%^&*()].*")) {
            return "Password must contain at least one special character (!@#$%^&*())";
        }

        // Check for at least one digit
        if (!password.matches(".*\\d.*")) {
            return "Password must contain at least one digit";
        }

        return "true"; // This indicates a valid password
    }

}
