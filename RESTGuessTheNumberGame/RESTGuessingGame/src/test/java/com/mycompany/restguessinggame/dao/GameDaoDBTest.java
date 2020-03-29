/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restguessinggame.dao;

import com.mycompany.restguessinggame.entity.Game;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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

public class GameDaoDBTest {

    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    @Autowired
    JdbcTemplate jdbc;

    public GameDaoDBTest() {
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
     * Test of addGame method, of class GameDaoDB.
     */
    @Test
    public void testAddGetGame() {
        Game g = new Game();
        g.setAnswer("1234");
        g = gameDao.addGame(g);

        Game fromDao = gameDao.getGameById(g.getId());

        assertEquals(fromDao, g);

    }

    /**
     * Test of getAllGames method, of class GameDaoDB.
     */
    @Test
    public void testGetAllGames() {
        Game g = new Game();
        g.setAnswer("1234");
        g = gameDao.addGame(g);

        Game g2 = new Game();
        g2.setAnswer("4321");
        g2 = gameDao.addGame(g2);

        List<Game> games = gameDao.getAllGames();

        assertEquals(2, games.size());
        assertTrue(games.contains(g));
        assertTrue(games.contains(g2));

    }

    /**
     * Test of updateGame method, of class GameDaoDB.
     */
    @Test
    public void testUpdateGame() {
        Game g = new Game();
        g.setAnswer("2468");
        g = gameDao.addGame(g);

        Game fromDao = gameDao.getGameById(g.getId());
        assertEquals(fromDao, g);

        g.setFinished(true);
        gameDao.updateGame(g);
        assertNotEquals(fromDao, g);

        fromDao = gameDao.getGameById(g.getId());
        assertEquals(fromDao, g);

    }

}
