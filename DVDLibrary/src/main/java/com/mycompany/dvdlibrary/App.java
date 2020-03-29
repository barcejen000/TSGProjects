/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary;

import com.mycompany.dvdlibrary.controller.DVDLibraryController;
import com.mycompany.dvdlibrary.dao.DVDLibraryDao;
import com.mycompany.dvdlibrary.dao.DVDLibraryDaoFileImpl;
import com.mycompany.dvdlibrary.ui.DVDLibraryView;
import com.mycompany.dvdlibrary.ui.UserIO;
import com.mycompany.dvdlibrary.ui.UserIOConsoleImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jenni
 */
public class App {
    
    public static void main(String[] args) {
        
//        UserIO io = new UserIOConsoleImpl();
//        DVDLibraryView view = new DVDLibraryView(io);
//        DVDLibraryDao dao = new DVDLibraryDaoFileImpl();
//        DVDLibraryController controller = new DVDLibraryController(view, dao);
//        controller.run();

    ApplicationContext appContext
                = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

       DVDLibraryController controller = appContext.getBean("controller", DVDLibraryController.class);
        controller.run();
    }
    
}
