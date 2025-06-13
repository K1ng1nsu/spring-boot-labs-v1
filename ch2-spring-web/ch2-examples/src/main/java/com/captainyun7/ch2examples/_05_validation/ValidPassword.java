package com.captainyun7.ch2examples._05_validation;


//8자리 이상
//특수문자 포함 (예: !@#$%^&*)
//영문 대문자 포함
//숫자 포함

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "비밀번호는 8자 이상, 숫자, 대문자, 특수문자를 포함해야 합니다.";
}
