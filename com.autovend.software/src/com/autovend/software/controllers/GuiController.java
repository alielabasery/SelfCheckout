package com.autovend.software.controllers;

import com.autovend.Barcode;
import com.autovend.Numeral;
import com.autovend.PriceLookUpCode;
import com.autovend.devices.SelfCheckoutStation;
import com.autovend.devices.SupervisionStation;
import com.autovend.products.BarcodedProduct;
import com.autovend.products.PLUCodedProduct;
import com.autovend.software.attendantgui.AttendantLogin;
import com.autovend.software.attendantgui.AttendantPanel;
import com.autovend.software.gui.*;

import Networking.NetworkController;
import com.autovend.software.utils.CodeUtils;
import data.DatabaseController;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.Currency;

public class GuiController {
    SelfCheckoutStation station;
    SupervisionStation attendantStation;
    JFrame attendantScreen;
    AttendentController attendantController;
    AttendantLoginLogoutController attendantLogin;

    public GuiController(SelfCheckoutStation station, SupervisionStation attendantStation) {
        this.station = station;
        this.attendantStation = attendantStation;
        attendantScreen = attendantStation.screen.getFrame();
        attendantController = new AttendentController();
        attendantLogin = new AttendantLoginLogoutController();
    }

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

    public void addItemsScreen() {
        JFrame screen = station.screen.getFrame();
        screen.setExtendedState(JFrame.NORMAL);
        screen.setPreferredSize(new Dimension(1280, 720));
        screen.getContentPane().removeAll();
        screen.setLayout(new BorderLayout());
        AddItemsPanel aip = new AddItemsPanel(this);
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

    public void membershipScreen() {
        JFrame screen = station.screen.getFrame();
        screen.setExtendedState(JFrame.NORMAL);
        screen.setPreferredSize(new Dimension(1280, 720));
        screen.getContentPane().removeAll();
        screen.setLayout(new BorderLayout());
        MembershipPanel hvp = new MembershipPanel(this);
        JPanel panel = new JPanel();
        panel.add(hvp);
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

    public void membershipDetailsScreen() {
        JFrame screen = station.screen.getFrame();
        screen.setExtendedState(JFrame.NORMAL);
        screen.setPreferredSize(new Dimension(1280, 720));
        screen.getContentPane().removeAll();
        screen.setLayout(new BorderLayout());
        MembershipDetailsPanel mdp = new MembershipDetailsPanel(this, new MembershipCardController());
        JPanel panel = new JPanel();
        panel.add(mdp);
        screen.getContentPane().add(panel, BorderLayout.CENTER);
        screen.pack();
        panel.add(mdp);
        screen.getContentPane().add(panel, BorderLayout.CENTER);
        screen.pack();
        // Make the JFrame display in the middle of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() - screen.getWidth()) / 2;
        int y = (int) (screenSize.getHeight() - screen.getHeight()) / 2;
        screen.setLocation(x, y);
        screen.validate();
        screen.setVisible(true);
    }

    public void checkoutScreen() {
        JFrame screen = station.screen.getFrame();
        screen.setExtendedState(JFrame.NORMAL);
        screen.setPreferredSize(new Dimension(1280, 720));
        screen.getContentPane().removeAll();
        screen.setLayout(new BorderLayout());
        CheckoutPanel cp = new CheckoutPanel();
        JPanel panel = new JPanel();
        panel.add(cp);
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
    	attendantScreen.setExtendedState(JFrame.NORMAL);
    	attendantScreen.setPreferredSize(new Dimension(1280, 720));
    	attendantScreen.getContentPane().removeAll();
    	attendantScreen.setLayout(new BorderLayout());
        AttendantLogin al = new AttendantLogin(this, attendantLogin);
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

    public void attendantScreen() {
    	attendantScreen.setExtendedState(JFrame.NORMAL);
    	attendantScreen.setPreferredSize(new Dimension(1280, 720));
    	attendantScreen.getContentPane().removeAll();
    	attendantScreen.setLayout(new BorderLayout());
        AttendantPanel ap = new AttendantPanel(this, attendantStation, attendantController, attendantLogin);
        JPanel panel = new JPanel();
        panel.add(ap);
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

    public void validateAttendantScreen() {
        attendantScreen.validate();
    }

    // Delete later
    public static void main(String[] args) {
    	try {        	
            PriceLookUpCode code = new PriceLookUpCode(new Numeral[] {Numeral.eight, Numeral.five, Numeral.six, Numeral.eight});
            DatabaseController.addPLUdProduct(code, new PLUCodedProduct(code, "Gala Apples", new BigDecimal(1.50)));
            code = new PriceLookUpCode(new Numeral[] {Numeral.eight, Numeral.two, Numeral.one, Numeral.one});
            DatabaseController.addPLUdProduct(code, new PLUCodedProduct(code, "Ambrosia Apples", new BigDecimal(1.89)));
            code = new PriceLookUpCode(new Numeral[] {Numeral.eight, Numeral.two, Numeral.eight, Numeral.six});
            DatabaseController.addPLUdProduct(code, new PLUCodedProduct(code, "Mcintosh Apples", new BigDecimal(2.25)));
            code = new PriceLookUpCode(new Numeral[] {Numeral.eight, Numeral.two, Numeral.three, Numeral.seven});
            DatabaseController.addPLUdProduct(code, new PLUCodedProduct(code, "Green Apples", new BigDecimal(0.89)));
            code = new PriceLookUpCode(new Numeral[] {Numeral.eight, Numeral.two, Numeral.one, Numeral.nine});
            DatabaseController.addPLUdProduct(code, new PLUCodedProduct(code, "Granny Smith Apples", new BigDecimal(1.35)));
            code = new PriceLookUpCode(new Numeral[] {Numeral.eight, Numeral.two, Numeral.five, Numeral.four});
            DatabaseController.addPLUdProduct(code, new PLUCodedProduct(code, "Honey Crisp Apples", new BigDecimal(1.99)));
            Barcode bcode = CodeUtils.stringBarcodeToBarcode("349896");
            DatabaseController.addBarcodedProduct(bcode, new BarcodedProduct(bcode, "Ham", new BigDecimal(15.00), 4.00));
            bcode = CodeUtils.stringBarcodeToBarcode("127634");
            DatabaseController.addBarcodedProduct(bcode, new BarcodedProduct(bcode, "Herbal Essance Shampoo", new BigDecimal(8.59), 2.09));
            code = CodeUtils.stringPLUToPLU("0001");
            DatabaseController.addPLUdProduct(code, new PLUCodedProduct(code, "Plastic bag", new BigDecimal(0.05)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    	
        Currency c = Currency.getInstance("CAD");
        int[] noteDenom = {5, 10, 20, 50, 100};
        BigDecimal[] coinDenom = {new BigDecimal("0.05"), new BigDecimal("0.1"), new BigDecimal("0.25"), new BigDecimal("0.5"), new BigDecimal("1"), new BigDecimal("2")};
        SelfCheckoutStation s = new SelfCheckoutStation(c, noteDenom, coinDenom, 10000, 1);
        SelfCheckoutStation s2 = new SelfCheckoutStation(c, noteDenom, coinDenom, 10000, 1);
        SelfCheckoutStation s3 = new SelfCheckoutStation(c, noteDenom, coinDenom, 10000, 1);
        
//        CheckoutController cc = new CheckoutController(s);
//        CheckoutController cc2 = new CheckoutController(s2);
//        CheckoutController cc3 = new CheckoutController(s3);
        
//        NetworkController.registerCheckoutStation("Station 1", cc);
//        NetworkController.registerCheckoutStation("Station 2", cc2);
//        NetworkController.registerCheckoutStation("Station 3", cc3);
        
        SupervisionStation ss = new SupervisionStation();
        
        GuiController gc = new GuiController(s, ss);
        // change below to gc.attendantLoginScreen to see the Attendant Station
        // change below to gc.startScreen to see the Customer Station
//        gc.attendantLoginScreen();
        gc.startScreen();
    }
}