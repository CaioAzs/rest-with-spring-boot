package com.restspringboot.azsrest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectNullException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;

    public RequiredObjectNullException(String message){
        super(message);
    }
    public RequiredObjectNullException(){
        super("You cannot persist a null user.");
    }

}
