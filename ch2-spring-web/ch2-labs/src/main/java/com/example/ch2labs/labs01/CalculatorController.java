package com.example.ch2labs.labs01;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {
    @GetMapping("/calc")
    public String calc(double x, double y, String op) {
        return switch (op) {
            case "add" -> "결과: " + x + " + " + y + " = " + x + y;
            case "sub" -> "결과: " + x + " - " + y + " = " + (x - y);
            case "mul" -> "결과: " + x + " * " + y + " = " + (x * y);
            case "div" -> "결과: " + x + " / " + y + " = " + (x / y);
            default -> "op must be 'add', 'sub', 'mul' or 'div'";
        };

    }
}
