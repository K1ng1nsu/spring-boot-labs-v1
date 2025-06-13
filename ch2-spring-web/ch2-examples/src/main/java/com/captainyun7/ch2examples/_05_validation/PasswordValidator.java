package com.captainyun7.ch2examples._05_validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;

        boolean lengthCheck = value.length() >= 8;
        boolean hasUppercase = value.matches(".*[A-Z].*");
        boolean hasDigit = value.matches(".*\\d.*");
        boolean hasSpecial = value.matches(".*[!@#$%^&*()].*");

        return lengthCheck && hasUppercase && hasDigit && hasSpecial;
    }
}