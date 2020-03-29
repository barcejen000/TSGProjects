/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine;

import com.mycompany.vendingmachine.controller.VendingMachineController;
import com.mycompany.vendingmachine.dao.VendingMachineAuditDao;
import com.mycompany.vendingmachine.dao.VendingMachineAuditDaoFileImpl;
import com.mycompany.vendingmachine.dao.VendingMachineDao;
import com.mycompany.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.mycompany.vendingmachine.service.VendingMachineService;
import com.mycompany.vendingmachine.ui.UserIO;
import com.mycompany.vendingmachine.ui.UserIOConsoleImpl;
import com.mycompany.vendingmachine.ui.VendingMachineView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jenni
 */
public class App {
    public static void main(String[] args) {
//        UserIO io = new UserIOConsoleImpl();
//        VendingMachineView view = new VendingMachineView(io);
//        
//        VendingMachineDao dao = new VendingMachineDaoFileImpl();
//        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoFileImpl();
//        VendingMachineService service = new VendingMachineService(dao, auditDao);
//        
//        VendingMachineController controller = new VendingMachineController(view, service);
//        controller.run();
    ApplicationContext appContext
                = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    
    VendingMachineController controller = appContext.getBean("controller",VendingMachineController.class);
    controller.run();
    } 
}
