/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restguessinggame.dao;

import com.mycompany.restguessinggame.entity.Round;
import java.util.List;

/**
 *
 * @author jenni
 */
public interface RoundDao {
    Round addRound(Round round);
    List<Round> getAllRoundsForGame(Integer gameId);
}
