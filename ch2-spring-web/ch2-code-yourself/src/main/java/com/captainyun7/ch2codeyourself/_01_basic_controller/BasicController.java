package com.captainyun7.ch2codeyourself._01_basic_controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Map;

@Controller
public class BasicController {


    @ResponseBody
    @GetMapping("/basic/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/basic/users/{userId}")
    @ResponseBody
    public String users(@PathVariable int userId) {
        return "userId: "+userId;
    }

    @GetMapping("/basic/users/{userId}/orders/{orderId}")
    @ResponseBody
    public String userOrder(@PathVariable int userId , @PathVariable int orderId) {
        return "userId: "+userId+" orderId: "+orderId;
    }

    @GetMapping("/basic/params")
    @ResponseBody
    public String params(@RequestParam String name, @RequestParam int age) {
        return "name: "+name+" age: "+age;
    }

    @GetMapping("/basic/filter")
    @ResponseBody
    public String filter(@RequestParam Map<String, String> params) {
        return params.toString();
    }

    @PostMapping("/basic/users")
    @ResponseBody
    public String usersPost(@RequestBody Map<String, String> params) {
        return params.toString();
    }

    @PutMapping("/basic/users/{userId}")
    @ResponseBody
    public String usersPut(@PathVariable int userId, @RequestBody Map<String, String> params) {
        return params.toString()+ " userId: "+userId;
    }

    @DeleteMapping("/basic/users/{userId}")
    @ResponseBody
    public String usersDelete(@PathVariable int userId,@RequestBody Map<String, String> params) {
        return params.toString()+"userId: "+userId;
    }

}
