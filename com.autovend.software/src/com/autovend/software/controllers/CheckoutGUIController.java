package com.autovend.software.controllers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.autovend.devices.TouchScreen;
import com.autovend.software.gui.AddItemsPanel;
import com.autovend.software.gui.CheckoutPanel;
import com.autovend.software.gui.MembershipDetailsPanel;
import com.autovend.software.gui.MembershipPanel;
import com.autovend.software.gui.StartScreenPanel;

public class CheckoutGUIController {
	private AttendentController attendant;
	private CheckoutController checkout;
	private TouchScreen screen;
	private JFrame frame;
	
	public CheckoutGUIController(CheckoutController checkout, AttendentController attendant) {
		this.checkout = checkout;
		this.attendant = attendant;
		this.screen = checkout.getSelfCheckoutStation().screen;
		this.frame = screen.getFrame();
	}
	
	public CheckoutController getCheckout() {
		return checkout;
	}
	
	private void resetScreen() {
        frame.setExtendedState(JFrame.NORMAL);
        frame.setPreferredSize(new Dimension(1280, 720));
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
	}
	
	private void centreAndShowScreen() {
        frame.pack();
        // Make the JFrame display in the middle of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() - frame.getWidth()) / 2;
        int y = (int) (screenSize.getHeight() - frame.getHeight()) / 2;
        frame.setLocation(x, y);
        frame.validate();
        screen.setVisible(true);
	}
	
	public void startScreen() {
		resetScreen();
		StartScreenPanel scp = new StartScreenPanel(this);
        JPanel panel = new JPanel();
        panel.add(scp);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        centreAndShowScreen();
	}
	
	public void addItemsScreen() {
		resetScreen();
		AddItemsPanel aip = new AddItemsPanel(this);
        JPanel panel = new JPanel();
        panel.add(aip);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        centreAndShowScreen();
	}
	
	public void membershipScreen() {
		resetScreen();
		MembershipPanel hvp = new MembershipPanel(this);
        JPanel panel = new JPanel();
        panel.add(hvp);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        centreAndShowScreen();
	}
	
	public void membershipDetailsScreen() {
		resetScreen();
		MembershipDetailsPanel mdp = new MembershipDetailsPanel(this, new MembershipCardController());
        JPanel panel = new JPanel();
        panel.add(mdp);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        centreAndShowScreen();
	}
	
	public void checkoutScreen() {
		resetScreen();
		CheckoutPanel cp = new CheckoutPanel();
        JPanel panel = new JPanel();
        panel.add(cp);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
	}
}
