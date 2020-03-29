/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.dao;

import com.mycompany.dvdlibrary.dto.DVD;
import java.util.List;

/**
 *
 * @author jenni
 */
public interface DVDLibraryDao {
    DVD addDVD(DVD dvd) throws DVDLibraryDaoException;
    DVD getDVDByTitle(String dvdTitle) throws DVDLibraryDaoException;
    List<DVD> getAllDVDS() throws DVDLibraryDaoException;
    DVD editDVD(DVD updatedDvd) throws DVDLibraryDaoException;
    DVD removeDVDByTitle(String dvdTitle) throws DVDLibraryDaoException;
    
}
