package com.captainyun7.ch2examples._05_validation;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    @NotBlank(message = "아이디는 필수입니다.")
    @Size(min = 5, max = 10, message = "5~10 자리 아이디를 입력하세요")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영문과 숫자만 가능합니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Min(value = 14, message = "만 14세 이상만 가입할 수 있습니다.")
    private Integer age;

    @AssertTrue(message = "약관에 동의해야 합니다.")
    private Boolean agreeTerms;
}
