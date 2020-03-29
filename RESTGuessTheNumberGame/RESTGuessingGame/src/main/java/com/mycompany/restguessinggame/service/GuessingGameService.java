/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restguessinggame.service;

import com.mycompany.restguessinggame.dao.GameDao;
import com.mycompany.restguessinggame.dao.RoundDao;
import com.mycompany.restguessinggame.entity.Game;
import com.mycompany.restguessinggame.entity.Round;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jenni
 */
@Service
public class GuessingGameService {

    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    public int startNewGame() {
        Game newGame = new Game();
        newGame.setAnswer(makeGameAnswer());
        newGame = gameDao.addGame(newGame);
        int gameId = newGame.getId();

        return gameId;
    }

    public Round addRound(Round round) throws InvalidDataException {
        String getGuessForRound = round.getGuess();

        if (getGuessForRound == null || getGuessForRound.isBlank()) {
            throw new InvalidDataException("Please make a valid guess, your guess cannot be blank");
        } else if (getGuessForRound.length() < 4 || getGuessForRound.length() > 4) {
            throw new InvalidDataException("Please make a valid guess, your guess must be 4 digits long ");
        } else if (!getGuessForRound.matches("^[0-9]*$")) {
            throw new InvalidDataException("Please make a valid guess, your guess must be numeric");
        } else if (hasDuplicateNumbers(getGuessForRound)) {
            throw new InvalidDataException("Please make a valid guess, your guess must consist of 4 unique numbers");
        }

        Game game = gameDao.getGameById(round.getGameId());
        String getGameAnswerForRound = game.getAnswer();
        String getResultsForRound = roundResults(getGuessForRound, getGameAnswerForRound);
        round.setResult(getResultsForRound);

        if (getGuessForRound.equals(getGameAnswerForRound)) {
            game.setFinished(true);
            gameDao.updateGame(game);
        }

        return roundDao.addRound(round);
    }

    public List<Game> getAllGames() {
        List<Game> games = gameDao.getAllGames();
        for (Game g : games) {
            if (g.isFinished() == false) {
                g.setAnswer("$$$$");
            }
        }

        return games;
    }

    public Game getGameById(Integer id) throws GameDoesNotExistException {
        Game g = gameDao.getGameById(id);
       
        if (g == null) {
         throw new GameDoesNotExistException("Please enter a valid game ID, no game exists for this ID");
        }
        
        if (g.isFinished() == false) {
            g.setAnswer("$$$$");
        }
        
        

        return g;

    }

    public List<Round> getAllRoundsForGame(Integer gameId) throws GameDoesNotExistException {
        List<Round> rounds = roundDao.getAllRoundsForGame(gameId);
        
        if (gameDao.getGameById(gameId) ==  null) {     
            throw new GameDoesNotExistException("Please enter a valid game ID, no rounds exists for this ID");
    } 
        return rounds;
    }

    private String makeGameAnswer() {
        String gameAnswer = "";
        Random random = new Random();
        gameAnswer = String.format("%04d", random.nextInt(10000));
        while (hasDuplicateNumbers(gameAnswer)) {
            gameAnswer = String.format("%04d", random.nextInt(10000));
        }

        return gameAnswer;

    }

    private boolean hasDuplicateNumbers(String numberString) {
        boolean notUnique = false;
        for (int i = 0; i < numberString.length(); i++) {
            for (int j = i + 1; j < numberString.length(); j++) {
                if (numberString.charAt(i) == numberString.charAt(j)) {
                    notUnique = true;
                }
            }
        }
        return notUnique;
    }

    private String roundResults(String roundGuess, String gameAnswer) {
        int exactsCount = 0;
        int partialsCount = 0;

        for (int i = 0; i < roundGuess.length(); i++) {
            if (roundGuess.charAt(i) == gameAnswer.charAt(i)) {
                exactsCount++;
            }
        }

        for (int j = 0; j < gameAnswer.length(); j++) {
            for (int k = 0; k < roundGuess.length(); k++) {
                if (j != k) {
                    if (gameAnswer.charAt(j) == roundGuess.charAt(k)) {
                        partialsCount++;
                    }
                }
            }
        }
        String roundResults = "E:" + exactsCount + ":P:" + partialsCount;

        return roundResults;
    }
}
