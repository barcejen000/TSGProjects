/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restguessinggame.dao;

import com.mycompany.restguessinggame.entity.Round;
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
public class RoundDaoDB implements RoundDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Round addRound(Round round) {
        jdbc.update("INSERT INTO round(gameId, guess, result) VALUES(?,?,?)",
                round.getGameId(),
                round.getGuess(),
                round.getResult());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setId(newId);

        return round;
    }

    @Override
    public List<Round> getAllRoundsForGame(Integer gameId) {
        try {
            List<Round> rounds = jdbc.query("SELECT * FROM round "
                    + "WHERE gameId = ? ORDER BY `time` ASC", new RoundMapper(), gameId);
            return rounds;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int i) throws SQLException {
            Round r = new Round();
            r.setId(rs.getInt("id"));
            r.setGameId(rs.getInt("gameId"));
            r.setGuess(rs.getString("guess"));
            r.setTime(rs.getTimestamp("time").toLocalDateTime());
            r.setResult(rs.getString("result"));
            return r;
        }

    }

}
