/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.dao;

import com.mycompany.dvdlibrary.dto.DVD;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author jenni
 */
public class DVDLibraryDaoFileImpl implements DVDLibraryDao {

    final String FILE = "dvdcollection.txt";
    final String DELIMITER = "::";

    List<DVD> dvds = new ArrayList<>();

    @Override
    public DVD addDVD(DVD dvd) throws DVDLibraryDaoException {
        try {
            loadFile();
        } catch (DVDLibraryDaoException ex) {
            //left blank because the write file is creating a file
        }
        dvds.add(dvd);
        writeFile();
        return dvd;
    }

    @Override
    public DVD getDVDByTitle(String dvdTitle) throws DVDLibraryDaoException {
        loadFile();
        for (DVD d : dvds) {
            if (d.getDvdTitle().equalsIgnoreCase(dvdTitle)) {
                return d;
            }
        }
        return null;
        
    }

    @Override
    public List<DVD> getAllDVDS() throws DVDLibraryDaoException {
        loadFile();
        return dvds;
        
    }

    @Override
    public DVD editDVD(DVD updatedDvd) throws DVDLibraryDaoException {
        for (DVD d : dvds) {
            if (d.getDvdTitle().equalsIgnoreCase(updatedDvd.getDvdTitle())) {
                d.setDvdTitle(updatedDvd.getDvdTitle());
                d.setReleaseYear(updatedDvd.getReleaseYear());
                d.setMpaaRating(updatedDvd.getMpaaRating());
                d.setDirectorName(updatedDvd.getDirectorName());
                d.setStudioName(updatedDvd.getStudioName());
                d.setUserRating(updatedDvd.getUserRating());
                writeFile();
                return updatedDvd;
            }
        }
        return null;
    }

    @Override
    public DVD removeDVDByTitle(String dvdTitle) throws DVDLibraryDaoException {
        loadFile();
        for (DVD d : dvds) {
            if (d.getDvdTitle().equalsIgnoreCase(dvdTitle)) {
                dvds.remove(d);
                writeFile();
                return d;
            }
        }
        return null;
    }

    private void loadFile() throws DVDLibraryDaoException {
        Scanner sc = null;

        try {
            sc = new Scanner(new FileReader(FILE));
        } catch (FileNotFoundException ex) {
            throw new DVDLibraryDaoException("File not found");
        }

        dvds = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(DELIMITER);
            DVD d = new DVD();
            d.setDvdTitle(parts[0]);
            d.setReleaseYear(Integer.parseInt(parts[1]));
            d.setMpaaRating(parts[2]);
            d.setDirectorName(parts[3]);
            d.setStudioName(parts[4]);
            d.setUserRating(parts[5]);

            dvds.add(d);
        }
        sc.close();
    }

    private void writeFile() throws DVDLibraryDaoException {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(FILE));
        } catch (IOException ex) {
            throw new DVDLibraryDaoException("Error writing to file");
        }

        for (DVD d : dvds) {
            pw.println(d.getDvdTitle() + DELIMITER + d.getReleaseYear()
                    + DELIMITER + d.getMpaaRating() + DELIMITER + d.getDirectorName()
                    + DELIMITER + d.getStudioName() + DELIMITER + d.getUserRating());
        }

        pw.flush();
        pw.close();
    }
      
}
