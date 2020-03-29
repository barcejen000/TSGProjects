/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restguessinggame.controller;

import com.mycompany.restguessinggame.entity.Game;
import com.mycompany.restguessinggame.entity.Round;
import com.mycompany.restguessinggame.service.GameDoesNotExistException;
import com.mycompany.restguessinggame.service.GuessingGameService;
import com.mycompany.restguessinggame.service.InvalidDataException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jenni
 */
@RestController
@RequestMapping("/api/gg")
public class GuessingGameController {

    @Autowired
    GuessingGameService service;

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)//set http status to 204
    public String startGame() {
        return "Welcome to the Guess the Number Game, your Game ID is " + service.startNewGame() + ".";

    }

    @PostMapping("/guess")
    public Round createRound(@RequestBody Round round) throws InvalidDataException {
        return service.addRound(round);
    }
    
    @GetMapping("/game")
    public List<Game> getAllGames(){
       return service.getAllGames();
    }
    
    @GetMapping("/game/{gameId}")
    public ResponseEntity<Game> getSpecificGameById(@PathVariable Integer gameId) throws GameDoesNotExistException{
        Game result = service.getGameById(gameId);
       
        if(result == null){
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
       
        return ResponseEntity.ok(result);
    }
    
    
    @GetMapping("/rounds/{gameId}")
    public List <Round> getAllRoundsForSpecifiedGame(@PathVariable Integer gameId) throws GameDoesNotExistException{
        return service.getAllRoundsForGame(gameId);
    }
    
}
