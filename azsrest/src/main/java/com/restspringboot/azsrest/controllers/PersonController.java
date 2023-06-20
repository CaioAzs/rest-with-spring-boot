package com.restspringboot.azsrest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.restspringboot.azsrest.exceptions.UnsupportedMathOperationException;

@RestController
public class PersonController {
    
    @GetMapping(value = "/sum/{number1}/{number2}")
    public Double sum(@PathVariable(value = "number1")String number1, @PathVariable(value = "number2")String number2) throws Exception {
        
       return 0D;
    }

}
