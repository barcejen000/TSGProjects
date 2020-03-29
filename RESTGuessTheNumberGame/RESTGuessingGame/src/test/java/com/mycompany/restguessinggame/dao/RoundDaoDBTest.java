/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restguessinggame.dao;

import com.mycompany.restguessinggame.entity.Game;
import com.mycompany.restguessinggame.entity.Round;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author jenni
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoundDaoDBTest {

    @Autowired
    RoundDao roundDao;

    @Autowired
    GameDao gameDao;

    @Autowired
    JdbcTemplate jdbc;

    public RoundDaoDBTest() {
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

//    
    /**
     * Test of getAllRounds method, of class RoundDaoDB.
     */
    @Test
    public void testForAddRoundAndGetAllRoundsForGame() {

        Game g = new Game();
        g.setAnswer("1234");
        g = gameDao.addGame(g);

        Round r = new Round();
        r.setGameId(g.getId());
        r.setGuess("1234");
        r.setResult("E:4:P:0");
        r = roundDao.addRound(r);
        
        Round r2 = new Round();
        r2.setGameId(g.getId());
        r2.setGuess("4321");
        r2.setResult("E:4:P:0");
        r2 = roundDao.addRound(r2);

        List<Round> roundsforGame = roundDao.getAllRoundsForGame(g.getId());
        assertEquals(2, roundsforGame.size());
        assertTrue(roundsforGame.contains(r));
        assertTrue(roundsforGame.contains(r2));
    }

}
