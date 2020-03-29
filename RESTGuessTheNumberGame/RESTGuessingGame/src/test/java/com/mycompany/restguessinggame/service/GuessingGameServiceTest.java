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
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author jenni
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GuessingGameServiceTest {

    @Autowired
    GuessingGameService service;

    @Autowired
    RoundDao roundDao;

    @Autowired
    GameDao gameDao;

    @Autowired
    JdbcTemplate jdbc;

    public GuessingGameServiceTest() {

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<Game> games = gameDao.getAllGames();
        for (Game g : games) {
                jdbc.update("DELETE from round where gameId = ? ", g.getId());
                jdbc.update("DELETE from game Where id = ? ", g.getId());
            }
        }

    @After
    public void tearDown() {
    }

    /**
     * Test of startNewGame method, of class GuessingGameService.
     */
    @Test
    public void testStartNewGame() throws Exception {
     Integer id = service.startNewGame();
     
     Game game = gameDao.getGameById(id);
     String answer = game.getAnswer();
     
     assertTrue(answer.length() == 4);
     assertTrue(answer.matches("^[0-9]*$"));
     
     assertTrue(game.getId() == id);
     assertNotNull(service.getGameById(id));
     
    }
    /**
     * Test of addRound method, of class GuessingGameService.
     */
    @Test
    public void testAddRound() throws Exception {
        Game g = new Game();
        g.setAnswer("1234");
        g = gameDao.addGame(g);

        try {
            Round r = new Round();
            r.setGameId(g.getId());
            r.setGuess("12345");
            r = service.addRound(r);
            fail();
        } catch (InvalidDataException ex) {
        }

        try {
            Round r2 = new Round();
            r2.setGameId(g.getId());
            r2.setGuess("123");
            r2 = service.addRound(r2);
            fail();
        } catch (InvalidDataException ex) {
        }

        try {
            Round r3 = new Round();
            r3.setGameId(g.getId());
            r3.setGuess("");
            r3 = service.addRound(r3);
            fail();
        } catch (InvalidDataException ex) {
        }

        try {
            Round r4 = new Round();
            r4.setGameId(g.getId());
            r4.setGuess(null);
            r4 = service.addRound(r4);
            fail();
        } catch (InvalidDataException ex) {
        }

        try {
            Round r5 = new Round();
            r5.setGameId(g.getId());
            r5.setGuess("2234");
            r5 = service.addRound(r5);
            fail();
        } catch (InvalidDataException ex) {
        }
        try {
            Round r6 = new Round();
            r6.setGameId(g.getId());
            r6.setGuess("hello");
            r6 = service.addRound(r6);
            fail();
        } catch (InvalidDataException ex) {
        }
        try {
            Round r7 = new Round();
            r7.setGameId(g.getId());
            r7.setGuess("8957");
            r7 = service.addRound(r7);
        } catch (InvalidDataException ex) {
            fail();
        }
        
        
        Game g2 = new Game();
        g2.setAnswer("5678");
        g2 = gameDao.addGame(g2);
            
        
        Round r8 = new Round();
        r8.setGameId(g2.getId());
        r8.setGuess("5790");
        r8 = service.addRound(r8);
        
        Round r9 = new Round();
        r9.setGameId(g2.getId());
        r9.setGuess("5678");
        r9 = service.addRound(r9);
        
        g2 = service.getGameById(g2.getId());
        
        assertEquals("E:1:P:1", r8.getResult());
        assertEquals("E:4:P:0", r9.getResult());
        assertEquals(true, g2.isFinished());
        
        
    }

//    /**
//     * Test of getAllGames method, of class GuessingGameService.
//     */
//    @Test
//    public void testGetAllGames() {
//    }

    /**
     * Test of getGameById method, of class GuessingGameService.
     */
    @Test
    public void testGetGameById() {
        Game g = new Game();;
        g.setAnswer("1234");
        g = gameDao.addGame(g);

        Integer gameId = 432;

        try {
            service.getGameById(gameId);
            fail();
        } catch (GameDoesNotExistException ex){
        }

        try {
            g = service.getGameById(g.getId());

        } catch (GameDoesNotExistException ex) {
            fail();
        }
        
        
        assertEquals("$$$$", g.getAnswer());
        }
    
    
    @Test
    public void testGetAllRoundsForGame() throws Exception {
        Game g = new Game();
        g.setAnswer("1234");
        g = gameDao.addGame(g);
        
        
        Round r = new Round();
        r.setGameId(g.getId());
        r.setGuess("8930");
        r = service.addRound(r);
        
        
        Round r2 = new Round();
        r2.setGameId(g.getId());
        r2.setGuess("9281");
        r2 = service.addRound(r2);
        
        
         try {
           service.getAllRoundsForGame(g.getId());

        } catch (GameDoesNotExistException ex) {
            fail();
        }
         
          try {
           service.getAllRoundsForGame(1234);
             fail();
        } catch (GameDoesNotExistException ex) {
        }
        
        
    }
}