/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restguessinggame.controller;

import com.mycompany.restguessinggame.service.GameDoesNotExistException;
import com.mycompany.restguessinggame.service.InvalidDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author jenni
 */
@ControllerAdvice
@RestController 
public class GuessingGameControllerExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Error> handleDataErrors(InvalidDataException ex, WebRequest request){
    String errorMessage = ex.getMessage();
    Error error = new Error(errorMessage);
    return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    
     @ExceptionHandler(GameDoesNotExistException.class)
    public ResponseEntity<Error> handleNonExistentData(GameDoesNotExistException ex, WebRequest request){
    String errorMessage = ex.getMessage();
    Error error = new Error(errorMessage);
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
}
