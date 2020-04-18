/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.dao;

import com.sg.supersightings.entities.Superperson;
import java.util.List;

/**
 *
 * @author jenni
 */
public interface SuperpersonDao {
    Superperson getSuperpersonById(int id);
    List<Superperson> getAllSuperpersons();
    Superperson addSuperperson(Superperson superperson);
    void updateSuperperson(Superperson superperson);
    void deleteSuperpersonById(int id);
}
