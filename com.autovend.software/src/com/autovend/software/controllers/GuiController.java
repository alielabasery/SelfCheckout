/** 
* Group Members: 
* 
* Ella Tomlinson (30140549)
* Kofi Frempong (30054189) 
* Adam Beauferris (30056865) 
* Niran Malla (30086877)
* Owen Tinning (30102041)
* Victor Campos Goitia (30106934)
* Zoe Kirsman (30113704) 
* Youssef Abdelrhafour (30085837) 
* James Rettie (30123362) 
* Rezwan Ahmed (30134609)
* Angeline Tran (30139846) 
* Saad Elkadri (30089084) 
* Dante Kirsman (30120778) 
* Riyad Abdullayev (30140509)
* Saksham Puri (30140617) 
* Faisal Islam (30140826)
* Naheen Kabir (30142101) 
* Jose Perales Rivera (30143354) 
* Aditi Yadav (30143652)
* Sahaj Malhotra () 
* Ali Elabasery (30148424)
* Fabiha Fairuzz Subha (30148674) 
* Umesh Oad (30152293)
* Daniel Boettcher (30153811) 
* Nam Nguyen Vu (30154892)
* 
*/
package com.autovend.software.controllers;

import com.autovend.devices.SelfCheckoutStation;
import com.autovend.devices.SupervisionStation;
import com.autovend.software.attendantgui.AttendantLogin;
import com.autovend.software.attendantgui.AttendantPanel;
import com.autovend.software.gui.AddItemsPanel;
import com.autovend.software.gui.StartScreenPanel;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.Currency;

public class GuiController {
    SelfCheckoutStation station;
    SupervisionStation attendantStation;
    JFrame attendantScreen;
    AttendentController attendantController;
    AttendantLoginLogoutController a;

    /**
     * GUI controller constructor
     * @param station
     * 		The station being displayed
     * @param attendantStation
     * 		The attendant station being displayed
     */
    public GuiController(SelfCheckoutStation station, SupervisionStation attendantStation) {
        this.station = station;
        this.attendantStation = attendantStation;
        attendantScreen = attendantStation.screen.getFrame();
        this.attendantController = new AttendentController();
        this.a = new AttendantLoginLogoutController();
    }

    /**
     * The start screen JFrame
     */
    public void startScreen() {
        JFrame screen = station.screen.getFrame();
        screen.setExtendedState(JFrame.NORMAL);
        screen.setPreferredSize(new Dimension(1280, 720));
        screen.getContentPane().removeAll();
        screen.setLayout(new BorderLayout());
        StartScreenPanel scp = new StartScreenPanel(this);
        JPanel panel = new JPanel();
        panel.add(scp);
        screen.getContentPane().add(panel, BorderLayout.CENTER);
        screen.pack();
        // Make the JFrame display in the middle of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() - screen.getWidth()) / 2;
        int y = (int) (screenSize.getHeight() - screen.getHeight()) / 2;
        screen.setLocation(x, y);
        screen.validate();
        station.screen.setVisible(true);
    }

    /**
     * The Add items JFrame screen
     */
    public void startToAddItemsScreen() {
        JFrame screen = station.screen.getFrame();
        screen.setExtendedState(JFrame.NORMAL);
        screen.setPreferredSize(new Dimension(1280, 720));
        screen.getContentPane().removeAll();
        screen.setLayout(new BorderLayout());
        AddItemsPanel aip = new AddItemsPanel();
        JPanel panel = new JPanel();
        panel.add(aip);
        screen.getContentPane().add(panel, BorderLayout.CENTER);
        screen.pack();
        // Make the JFrame display in the middle of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() - screen.getWidth()) / 2;
        int y = (int) (screenSize.getHeight() - screen.getHeight()) / 2;
        screen.setLocation(x, y);
        screen.validate();
        station.screen.setVisible(true);
    }

    // Attendant Station
    public void attendantLoginScreen() {
        attendantScreen = attendantStation.screen.getFrame();
        attendantScreen.setExtendedState(JFrame.NORMAL);
        attendantScreen.setPreferredSize(new Dimension(1280, 720));
        attendantScreen.getContentPane().removeAll();
        attendantScreen.setLayout(new BorderLayout());
        AttendantLogin al = new AttendantLogin(this);
        JPanel panel = new JPanel();
        panel.add(al);
        attendantScreen.getContentPane().add(panel, BorderLayout.CENTER);
        attendantScreen.pack();
        // Make the JFrame display in the middle of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() - attendantScreen.getWidth()) / 2;
        int y = (int) (screenSize.getHeight() - attendantScreen.getHeight()) / 2;
        attendantScreen.setLocation(x, y);
        attendantScreen.validate();
        attendantStation.screen.setVisible(true);
    }

    public void attendantLoginToAttendantScreen() {
        JFrame attendantScreen = attendantStation.screen.getFrame();
        attendantScreen.setExtendedState(JFrame.NORMAL);
        attendantScreen.setPreferredSize(new Dimension(1280, 720));
        attendantScreen.getContentPane().removeAll();
        attendantScreen.setLayout(new BorderLayout());
        AttendantPanel ap = new AttendantPanel(this, attendantStation, attendantController, a);
        JPanel panel = new JPanel();
        panel.add(ap);
        attendantScreen.getContentPane().add(panel, BorderLayout.CENTER);
        attendantScreen.pack();
        // Make the JFrame display in the middle of the attendantScreen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() - attendantScreen.getWidth()) / 2;
        int y = (int) (screenSize.getHeight() - attendantScreen.getHeight()) / 2;
        attendantScreen.setLocation(x, y);
        attendantScreen.validate();
        attendantStation.screen.setVisible(true);
    }
    
    public void validateAttendantScreen() {
    	attendantScreen.validate();
    }

    // Delete later
    public static void main(String[] args) {
        Currency c = Currency.getInstance("CAD");
        int[] noteDenom = {5, 10, 20, 50, 100};
        BigDecimal[] coinDenom = {new BigDecimal("0.05"), new BigDecimal("0.1"), new BigDecimal("0.25"), new BigDecimal("0.5"), new BigDecimal("1"), new BigDecimal("2")};
        SelfCheckoutStation s = new SelfCheckoutStation(c, noteDenom, coinDenom, 10000, 1);
        
        SupervisionStation ss = new SupervisionStation();
        
        GuiController gc = new GuiController(s, ss);
        // change below to gc.attendantLoginScreen to see the Attendant Station
        // change below to gc.startScreen to see the Customer Station
        gc.attendantLoginScreen();
        //gc.startScreen();
    }
}
