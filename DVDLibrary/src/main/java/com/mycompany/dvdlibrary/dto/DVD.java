/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.dto;

/**
 *
 * @author jenni
 */
public class DVD {
    private String dvdTitle;
    private int releaseYear;
    private String mpaaRating;
    private String directorName;
    private String studioName;
    private String userRating;
    
    
    public DVD (String dvdTitle, int releaseYear, String mpaaRating, String directorName, String studioName, String userRating){
        this.dvdTitle = dvdTitle;
        this.releaseYear = releaseYear;
        this.mpaaRating = mpaaRating;
        this.directorName = directorName;
        this.studioName = studioName;
        this.userRating = userRating;
    }
    
    public DVD(){
    }
    /**
     * @return the dvdTitle
     */
    public String getDvdTitle() {
        return dvdTitle;
    }

    /**
     * @param dvdTitle the dvdTitle to set
     */
    public void setDvdTitle(String dvdTitle) {
        this.dvdTitle = dvdTitle;
    }

    /**
     * @return the releaseYear
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * @param releaseYear the releaseYear to set
     */
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * @return the mpaaRating
     */
    public String getMpaaRating() {
        return mpaaRating;
    }

    /**
     * @param mpaaRating the mpaaRating to set
     */
    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    /**
     * @return the directorName
     */
    public String getDirectorName() {
        return directorName;
    }

    /**
     * @param directorName the directorName to set
     */
    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    /**
     * @return the userRating
     */
    public String getUserRating() {
        return userRating;
    }

    /**
     * @param userRating the userRating to set
     */
    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    /**
     * @return the studioName
     */
    public String getStudioName() {
        return studioName;
    }

    /**
     * @param studioName the studioName to set
     */
    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }
    
    
}
