package com.autovend.software.controllers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.autovend.devices.TouchScreen;
import com.autovend.software.attendantgui.AttendantLogin;
import com.autovend.software.attendantgui.AttendantPanel;

public class AttendantGUIController {
	private AttendentController attendant;
	private AttendantLoginLogoutController attendantLogin;
	private TouchScreen screen;
	private JFrame frame;
	
	public AttendantGUIController(AttendentController attendant) {
		this.attendant = attendant;
		this.screen = attendant.getSupervisionStation().screen;
		this.frame = screen.getFrame();
		attendantLogin = new AttendantLoginLogoutController();
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
	
	public void attendantLoginScreen() {
		resetScreen();
		AttendantLogin al = new AttendantLogin(this);
        JPanel panel = new JPanel();
        panel.add(al);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        centreAndShowScreen();
	}
	
    public void attendantScreen() {
    	resetScreen();
        AttendantPanel ap = new AttendantPanel(this);
        JPanel panel = new JPanel();
        panel.add(ap);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        centreAndShowScreen();
    }
    
    public AttendentController getAttendant() {
    	return attendant;
    }
    
    public AttendantLoginLogoutController getLoginController() {
    	return attendantLogin;
    }
}
