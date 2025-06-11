package com.captainyun7.ch2codeyourself._02_mvc_controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/mvc")
public class MvcController {

    @GetMapping("/basic/view")
//    @RequestMapping(value = "/basic/view",method = RequestMethod.GET)
    public String basicView() {
        System.out.println("basicView");
        return "basic";
    }

    @GetMapping("/model")
    public String home(Model model) {
      model.addAttribute("message", "Hello World");

      return "model";
    }

}
