/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restguessinggame.dao;

import com.mycompany.restguessinggame.entity.Game;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jenni
 */
@Repository
public class GameDaoDB implements GameDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Game addGame(Game game) {
        jdbc.update("INSERT INTO game(answer) VALUES(?)", game.getAnswer());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setId(newId);
        return game;
    }


    @Override
    public List<Game> getAllGames() {
        List<Game> games = jdbc.query("SELECT * FROM game", new GameMapper());
        return games;
    }

    @Override
    public Game getGameById(Integer id) {
        try {
            Game g = jdbc.queryForObject("SELECT * FROM game WHERE id = ?", new GameMapper(), id);
            return g;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public boolean updateGame(Game game) {
        return jdbc.update("UPDATE game SET finished = ? WHERE id = ?",
                game.isFinished(),
                game.getId()) > 0;
        
    }

    
    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int i) throws SQLException {
            Game g = new Game();
            g.setId(rs.getInt("id"));
            g.setAnswer(rs.getString("answer"));
            g.setFinished(rs.getBoolean("finished"));
            return g;
        }

    }

}
