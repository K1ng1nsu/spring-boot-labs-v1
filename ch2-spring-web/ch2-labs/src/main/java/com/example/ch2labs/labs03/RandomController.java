package com.example.ch2labs.labs03;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RandomController {
    @GetMapping("/random")
    public String random(int min, int max) {
        return "{ \"number\": "+new CustomRandom(min,max) +" }";
    }
}
