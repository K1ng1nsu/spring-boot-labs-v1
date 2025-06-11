package com.example.ch2labs.labs02;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class DiceController {
    @GetMapping("/dice")
    public String dice() {
        Random rand = new Random();
        return rand.nextInt(6) + 1 + "";
    }

}
