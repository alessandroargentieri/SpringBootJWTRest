package com.example.bytecode.SpringBootJWT.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UiController {

    //TODO

    @RequestMapping("/")
    @ResponseBody
    public String goToLogin(){
        return "login";
    }


}
