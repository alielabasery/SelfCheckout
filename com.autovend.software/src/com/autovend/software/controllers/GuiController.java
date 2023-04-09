package com.autovend.software.controllers;

import com.autovend.devices.SelfCheckoutStation;
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

    public GuiController(SelfCheckoutStation station) {
        this.station = station;
    }

    public void startScreen() {
        JFrame screen = station.screen.getFrame();
        screen.setPreferredSize(screen.getSize());
        screen.getContentPane().removeAll();
        screen.setLayout(new BorderLayout());
        StartScreenPanel scp = new StartScreenPanel(this);
        JPanel panel = new JPanel();
        panel.add(scp);
        screen.getContentPane().add(panel, BorderLayout.CENTER);
        screen.pack();
        screen.validate();
        station.screen.setVisible(true);
    }

    public void startToAddItemsScreen() {
        JFrame screen = station.screen.getFrame();
        screen.setPreferredSize(screen.getSize());
        screen.getContentPane().removeAll();
        screen.setLayout(new BorderLayout());
        AddItemsPanel aip = new AddItemsPanel();
        JPanel panel = new JPanel();
        panel.add(aip);
        screen.getContentPane().add(panel, BorderLayout.CENTER);
        screen.pack();
        screen.validate();
        station.screen.setVisible(true);
    }

    // Attendant Station
    public void attendantLoginScreen() {
        JFrame screen = station.screen.getFrame();
        screen.setPreferredSize(screen.getSize());
        screen.getContentPane().removeAll();
        screen.setLayout(new BorderLayout());
        AttendantLogin al = new AttendantLogin(this);
        JPanel panel = new JPanel();
        panel.add(al);
        screen.getContentPane().add(panel, BorderLayout.CENTER);
        screen.pack();
        screen.validate();
        station.screen.setVisible(true);
    }

    public void attendantLoginToAttendantScreen() {
        JFrame screen = station.screen.getFrame();
        screen.setPreferredSize(screen.getSize());
        screen.getContentPane().removeAll();
        screen.setLayout(new BorderLayout());
        AttendantPanel ap = new AttendantPanel();
        JPanel panel = new JPanel();
        panel.add(ap);
        screen.getContentPane().add(panel, BorderLayout.CENTER);
        screen.pack();
        screen.validate();
        station.screen.setVisible(true);
    }

    // Delete later
    public static void main(String[] args) {
        Currency c = Currency.getInstance("CAD");
        int[] noteDenom = {5, 10, 20, 50, 100};
        BigDecimal[] coinDenom = {new BigDecimal("0.05"), new BigDecimal("0.1"), new BigDecimal("0.25"), new BigDecimal("0.5"), new BigDecimal("1"), new BigDecimal("2")};
        SelfCheckoutStation s = new SelfCheckoutStation(c, noteDenom, coinDenom, 10000, 1);
        GuiController gc = new GuiController(s);
        // change below to gc.attendantLoginScreen to see the Attendant Station
        // change below to gc.startScreen to see the Customer Station
        gc.attendantLoginToAttendantScreen();
    }
}
