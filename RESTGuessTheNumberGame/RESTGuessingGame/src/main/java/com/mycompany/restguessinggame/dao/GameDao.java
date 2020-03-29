/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restguessinggame.dao;

import com.mycompany.restguessinggame.entity.Game;
import java.util.List;

/**
 *
 * @author jenni
 */
public interface GameDao {
    Game addGame(Game game);
    List<Game> getAllGames();
    Game getGameById(Integer id);
    boolean updateGame(Game game);
}
