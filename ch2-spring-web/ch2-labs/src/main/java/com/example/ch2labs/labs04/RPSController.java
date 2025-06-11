package com.example.ch2labs.labs04;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class RPSController {
    @GetMapping("/rps")
    public String rps(String user) {
        Map<String, String> map = new HashMap<>();
        map.put("user", user);
        Random rand = new Random();
        int i = rand.nextInt(3);
        if(i == 0) map.put("server", "rock");
        if(i == 1) map.put("server", "scissors");
        if(i == 2) map.put("server", "paper");


        if(user.equals("rock")&& i == 0) map.put("result","Tie!");
        if(user.equals("rock")&& i == 1) map.put("result","You Win!");
        if(user.equals("rock")&& i == 2) map.put("result","You Lose!");

        if(user.equals("scissors")&& i == 0) map.put("result","You Lose!");
        if(user.equals("scissors")&& i == 1) map.put("result","Tie!");
        if(user.equals("scissors")&& i == 2) map.put("result","You Win!");

        if(user.equals("paper")&& i == 0) map.put("result","You Win!");
        if(user.equals("paper")&& i == 1) map.put("result","You Lose!");
        if(user.equals("paper")&& i == 2) map.put("result","Tie!");





        return map.toString();
    }
}
