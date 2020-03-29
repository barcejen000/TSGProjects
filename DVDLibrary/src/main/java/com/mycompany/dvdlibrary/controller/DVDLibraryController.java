/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.controller;

import com.mycompany.dvdlibrary.dao.DVDLibraryDao;
import com.mycompany.dvdlibrary.dao.DVDLibraryDaoException;
import com.mycompany.dvdlibrary.dto.DVD;
import com.mycompany.dvdlibrary.ui.DVDLibraryView;
import java.util.List;

/**
 *
 * @author jenni
 */
public class DVDLibraryController {

    private DVDLibraryView view;
    private DVDLibraryDao dao;

    public DVDLibraryController(DVDLibraryView view, DVDLibraryDao dao) {
        this.view = view;
        this.dao = dao;
    }

    public void run() {
        boolean keepGoing = true;
        int menuChoice = 0;

        while (keepGoing) {
            try {
                menuChoice = getMenuChoice();

                switch (menuChoice) {

                    case 1:
                        displayAllDvds();
                        break;
                    case 2:
                        addDvd();
                        break;
                    case 3:
                        viewDvd();
                        break;
                    case 4:
                        removeDvd();
                        break;
                    case 5:
                        editDvd();
                        break;
                    case 6:
                        exit();
                        System.exit(0);
                }
            } catch (DVDLibraryDaoException ex) {
                view.printError(ex.getMessage());
            }
        }
    }

    private int getMenuChoice() {
        return view.printMenuAndGetChoice();
    }

    public void displayAllDvds() throws DVDLibraryDaoException {
        view.displayAllDvdsBanner();
        List<DVD> dvds = dao.getAllDVDS();
        view.displayAllDvds(dvds);
    }

    public void addDvd() throws DVDLibraryDaoException {
        view.displayCreateDvdBanner();
        DVD d = view.createNewDvd();
        dao.addDVD(d);
        view.displayCreateSucessBanner();
    }

    private void viewDvd() throws DVDLibraryDaoException {
        String dvdTitle = view.getSelectedDvd();
        view.displaySelectedDvdBanner();
        DVD dvd = dao.getDVDByTitle(dvdTitle);
        view.displaySelectedDvd(dvd);
    }

    private void removeDvd() throws DVDLibraryDaoException {
        view.displayRemoveDvdBanner();
        String dvdTitle = view.getSelectedDvdToRemove();
        DVD removedDvd = dao.removeDVDByTitle(dvdTitle);
        view.displayRemoveMessages(removedDvd);
    }

    private void editDvd() throws DVDLibraryDaoException {
        view.displayEditDvdBanner();
        String dvdTitle = view.getSelectedDvdToEdit();
        DVD updatedDvd = dao.getDVDByTitle(dvdTitle);
        view.displayEditMessages(updatedDvd);
        boolean keepGoing = true;
        if(updatedDvd != null){
        while (keepGoing) {
            int editSelection = view.printEditMenuAndGetNewEntry(updatedDvd);
            switch (editSelection) {
                case 1:
                    updatedDvd.setDvdTitle(view.getUpdatedTitle());
                    view.diplayEditSuccessBanner();
                    break;
                case 2:
                    updatedDvd.setReleaseYear(view.getUpdatedYear());
                    view.diplayEditSuccessBanner();
                    break;
                case 3:
                    updatedDvd.setMpaaRating(view.getUpdatedMpaaRating());
                    view.diplayEditSuccessBanner();
                    break;
                case 4:
                    updatedDvd.setDirectorName(view.getUpdatedDirectorName());
                    view.diplayEditSuccessBanner();
                    break;
                case 5:
                    updatedDvd.setStudioName(view.getUpdatedStudioName());
                    view.diplayEditSuccessBanner();
                    break;
                case 6:
                    updatedDvd.setUserRating(view.getUpdatedUserRating());
                    view.diplayEditSuccessBanner();
                    break;
                case 7:
                    keepGoing = false;
                    break;

            }

        }
         DVD dvd = dao.editDVD(updatedDvd);
    }
    }
    
    private void exit() {
        view.displayExitMessage();
    }

}
