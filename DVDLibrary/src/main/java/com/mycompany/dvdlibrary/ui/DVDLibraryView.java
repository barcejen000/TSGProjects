/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.ui;

import com.mycompany.dvdlibrary.dto.DVD;
import java.util.List;

/**
 *
 * @author jenni
 */
public class DVDLibraryView {

    private UserIO io;

    public DVDLibraryView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetChoice() {

        io.print("Main Menu");
        io.print("1. View all DVD's in the collection");
        io.print("2. Add a DVD");
        io.print("3. Search the library for a DVD");
        io.print("4. Remove a DVD from the collection");
        io.print("5. Edit a DVD entry");
        io.print("6. Exit");

        return io.readInt("Please select from the above choices.", 1, 6);
    }

    public void displayAllDvdsBanner() {
        io.print("=== Displaying all DVD's in the collection ===");
    }

    public void displayAllDvds(List<DVD> dvds) {
        for (DVD d : dvds) {
            io.print(d.getDvdTitle() + " - " + d.getReleaseYear());
        }
    }

    public DVD createNewDvd() {
        String dvdTitle = io.readString("Please enter the DVD title: ");
        int releaseYear = io.readInt("Please enter the release year of the DVD: ");
        String mpaaRating = io.readString("Please enter the DVD's MPAA Rating: ");
        String directorName = io.readString("Please enter the name of the director: ");
        String studioName = io.readString("Please enter the studio name: ");
        String userRating = io.readString("Please enter your overall rating of this DVD or any comments you have about the DVD: ");

        DVD d = new DVD(dvdTitle, releaseYear, mpaaRating, directorName, studioName, userRating);
        return d;
    }

    public void displayCreateDvdBanner() {
        io.print("=== Create New DVD Entry ===");
    }

    public void displayCreateSucessBanner() {
        io.readString("DVD entry has been added successfully! Please hit enter to continue.");
    }

    public void displaySelectedDvdBanner() {
        io.print("=== Displaying Search Results ===");
    }

    public String getSelectedDvd() {
        return io.readString("Please enter the title of the DVD you are searching for: ");
    }

    public void displaySelectedDvd(DVD dvdTitle) {
        if (dvdTitle != null) {
            io.print("DVD Title: " + dvdTitle.getDvdTitle());
            io.print("Release Year: " + dvdTitle.getReleaseYear());
            io.print("MPAA Rating: " + dvdTitle.getMpaaRating());
            io.print("Director Name: " + dvdTitle.getDirectorName());
            io.print("Studio: " + dvdTitle.getStudioName());
            io.print("Viewers rating/comments: " + dvdTitle.getUserRating());
            io.print("");
        } else {
            io.print("No such DVD Title exists in this library.");
        }
        io.readString("Please hit enter to continue");
    }

    public void displayRemoveDvdBanner() {
        io.print("=== Remove DVD from Library ===");
    }

    public void displayRemoveMessages(DVD dvdEntry) {
        if (dvdEntry != null) {
            io.print("DVD has been successfully removed from the library.");
        } else {
            io.print("No such DVD title exists in the library.");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayExitMessage() {
        io.print("Exiting program, thanks for using it!");
    }

    public void printError(String message) {
        io.print("ERROR: " + message);
    }

    public void displayEditDvdBanner() {
        io.print("=== Edit an existing DVD in the Library ===");
    }

    public int printEditMenuAndGetNewEntry(DVD selectedDvd) {
        io.print("Edit Menu");
        io.print("1. Edit DVD Title: " + selectedDvd.getDvdTitle());
        io.print("2. Edit Release Year: " + selectedDvd.getReleaseYear());
        io.print("3. Edit MPAA Rating: " + selectedDvd.getMpaaRating());
        io.print("4. Edit Director Name: " + selectedDvd.getDirectorName());
        io.print("5. Edit Studio Name: " + selectedDvd.getStudioName());
        io.print("6. Edit viewer rating/comments: " + selectedDvd.getUserRating());
        io.print("7. Return to Main Menu");

        return io.readInt("Please select from the above choices.", 1, 7);
    }

    public void diplayEditSuccessBanner() {
        io.print("DVD entry has been edited successfully!");
    }

    public String getSelectedDvdToEdit() {
        return io.readString("Please enter the title of the DVD you wish to edit: ");
    }

    public String getSelectedDvdToRemove() {
        return io.readString("Please enter the title of the DVD you wish to remove from the library: ");
    }

    public void displayEditMessages(DVD dvdEntry) {
        if (dvdEntry != null) {
            io.print("Now editing DVD entry for: " + dvdEntry.getDvdTitle());
        } else {
            io.print("No such DVD title exists in the library.");
        }
    }

    public String getUpdatedTitle() {
        return io.readString("Enter the updated title of the DVD: ");
    }

    public int getUpdatedYear() {
        return io.readInt("Enter the updated release year: ");
    }

    public String getUpdatedMpaaRating() {
        return io.readString("Enter the updated MPAA rating: ");
    }

    public String getUpdatedDirectorName() {
        return io.readString("Enter the updated director name: ");
    }

    public String getUpdatedStudioName() {
        return io.readString("Enter the updated studio name: ");

    }

    public String getUpdatedUserRating() {
        return io.readString("Enter your review/any comments you may have: ");
    }

}
