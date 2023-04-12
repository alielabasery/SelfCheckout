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
        JFrame screen = attendantStation.screen.getFrame();
        screen.setExtendedState(JFrame.NORMAL);
        screen.setPreferredSize(new Dimension(1280, 720));
        screen.getContentPane().removeAll();
        screen.setLayout(new BorderLayout());
        AttendantLogin al = new AttendantLogin(this);
        JPanel panel = new JPanel();
        panel.add(al);
        screen.getContentPane().add(panel, BorderLayout.CENTER);
        screen.pack();
        // Make the JFrame display in the middle of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() - screen.getWidth()) / 2;
        int y = (int) (screenSize.getHeight() - screen.getHeight()) / 2;
        screen.setLocation(x, y);
        screen.validate();
        attendantStation.screen.setVisible(true);
    }

    public void attendantLoginToAttendantScreen() {
        JFrame screen = attendantStation.screen.getFrame();
        screen.setExtendedState(JFrame.NORMAL);
        screen.setPreferredSize(new Dimension(1280, 720));
        screen.getContentPane().removeAll();
        screen.setLayout(new BorderLayout());
        AttendantPanel ap = new AttendantPanel(this, attendantStation);
        JPanel panel = new JPanel();
        panel.add(ap);
        screen.getContentPane().add(panel, BorderLayout.CENTER);
        screen.pack();
        // Make the JFrame display in the middle of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() - screen.getWidth()) / 2;
        int y = (int) (screenSize.getHeight() - screen.getHeight()) / 2;
        screen.setLocation(x, y);
        screen.validate();
        attendantStation.screen.setVisible(true);
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
