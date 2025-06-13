package com.captainyun7.ch2examples._05_validation;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/validation")
public class UserController {

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequest request) {
        return ResponseEntity.ok("회원가입 성공!");
    }

    @PostMapping("/signup2")
    public ResponseEntity<?> signup2(@RequestBody @Valid SignupRequest request, BindingResult result) {
        if (result.hasErrors()) {
            // 첫 번째 에러 메시지만 추출
            String errorMessage = result.getFieldErrors().get(0).getDefaultMessage();

            Map<String, Object> response = Map.of(
                    "status", "fail",
                    "message", errorMessage
            );
            return ResponseEntity.ok(response);
        }

        // 회원가입 로직 생략
        Map<String, Object> response = Map.of(
                "status", "success",
                "message", "회원가입이 완료되었습니다."
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/strong-signup")
    public ResponseEntity<String> strongSignup(@RequestBody @Valid StrongSignupRequest request) {
        return ResponseEntity.ok("회원가입 성공!");
    }



}
