package com.autovend.software.attendantgui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import com.autovend.devices.SupervisionStation;
import com.autovend.software.PreventStation;
import com.autovend.software.controllers.AttendantLoginLogoutController;
import com.autovend.software.controllers.AttendantShutdownStationController;
import com.autovend.software.controllers.AttendentController;
import com.autovend.software.controllers.CheckoutController;
import com.autovend.software.controllers.GuiController;
import com.autovend.software.controllers.StartUpRoutineController;
import com.autovend.software.gui.dlgSearchProduct;

import Networking.NetworkController;

public class AttendantPanel extends JPanel {
	GuiController gc;
	SupervisionStation attendantStation;
	AttendentController attendantController;
	AttendantLoginLogoutController loginController;
	AttendantShutdownStationController shutdownController;
	StartUpRoutineController startupController;
	
	private ArrayList<String> unsupervisedStations = new ArrayList<String>();
	private static ArrayList<String> shutdownStations = new ArrayList<String>();
	
	Box stationsBox;
	
    int stationCounter = 0;
        
	public AttendantPanel(GuiController gc, SupervisionStation attendantStation, 
			AttendentController attendantController, AttendantLoginLogoutController a) {
		this.gc = gc;
		this.attendantStation = attendantStation;
		this.attendantController = attendantController;
		this.loginController = a;
		this.shutdownController = new AttendantShutdownStationController(attendantStation, a);
		this.startupController = new StartUpRoutineController(attendantStation, a);
		
		setPreferredSize(new Dimension(1280, 720));
		setLayout(null);
		
		Panel stationsPanel = new Panel();
		stationsPanel.setBounds(469, 52, 801, 587);
		stationsPanel.setLayout(new BorderLayout(0, 0));
		add(stationsPanel);
		
		stationsBox = Box.createVerticalBox();
		JScrollPane stationsScrollPane = new JScrollPane(stationsBox);
		stationsScrollPane.setPreferredSize(new Dimension(801, 587));
		stationsPanel.add(stationsScrollPane, BorderLayout.NORTH);
		
		updateStationsOnScreen(null);
		
		JLabel stationsLabel = new JLabel("Supervised Stations");
		stationsLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		stationsLabel.setBounds(469, 10, 183, 33);
		add(stationsLabel);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		logoutButton.setBounds(1185, 20, 85, 21);
		logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	gc.attendantLoginScreen();
            }
        });
		add(logoutButton);
		
		JButton addStationButton = new JButton("Add New Station");
		addStationButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addStationButton.setBounds(801, 661, 156, 27);
		addStationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	Object[] choices = unsupervisedStations.toArray();
            	
            	String input = (String) JOptionPane.showInputDialog(null, "Choose an unsupervised Station from below: ",
            			"Add Station", JOptionPane.QUESTION_MESSAGE, null, choices, unsupervisedStations.get(0));
            	
            	if (input != null) {
            		CheckoutController checkoutController = NetworkController.getCheckoutStationController(input);
            		if (checkoutController != null) {
            			attendantStation.add(checkoutController.getSelfCheckoutStation());
            			unsupervisedStations.remove(input);
            			updateStationsOnScreen(input);
            		}
            	}
            }
        });
		add(addStationButton);
		
		JLabel notificationsLabel = new JLabel("Notifications");
		notificationsLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		notificationsLabel.setBounds(10, 10, 183, 33);
		add(notificationsLabel);
		
		Panel notificationsPanel = new Panel();
		notificationsPanel.setBounds(10, 52, 453, 587);
		notificationsPanel.setLayout(new BorderLayout(0, 0));
		add(notificationsPanel);
		
		Box notificationsBox = Box.createVerticalBox();
		JScrollPane notificationsScrollPanel = new JScrollPane(notificationsBox);
		notificationsScrollPanel.setPreferredSize(new Dimension(801, 587));
		notificationsPanel.add(notificationsScrollPanel, BorderLayout.NORTH);
		
		for (int i = 0; i < 2; i++) {
			notificationsBox.add(getNotificationPanels());
			notificationsBox.add(new JSeparator());
		}
	}
	
	public void updateStationsOnScreen(String stationName) {
		if (stationName == null) {
			List<String> stationNames = NetworkController.getCheckoutStationNames();
	
			for (int i = 0; i < stationNames.size(); i++) {
				CheckoutController checkoutController = NetworkController.getCheckoutStationController(stationNames.get(i));
				if (checkoutController != null) {
					if (attendantStation.supervisedStations().contains(checkoutController.getSelfCheckoutStation())) {
						addStation(stationNames.get(i));
					} else {
						unsupervisedStations.add(stationNames.get(i));
					}
				}
			}
		} else {
			addStation(stationName);
		}
	}
	
	public void addStation(String name) {
		Component[] temp = getStationPanels(name);
		stationsBox.add(temp[0]);
		stationsBox.add(temp[1]);
		updateScreen();
	}
	
	public void removeStation(JPanel panel, JSeparator separator) {
		stationsBox.remove(panel);
		stationsBox.remove(separator);
		updateScreen();
	}
	
	private void updateScreen() {
		gc.validateAttendantScreen();
		repaint();
	}
	
	public Component[] getStationPanels(String name) {
		JPanel panel = new JPanel();
		JSeparator separator = new JSeparator();
		
		JLabel label = new JLabel(name);
		panel.add(label);
		panel.add(Box.createRigidArea(new Dimension(100, 0)));
		
		JButton bttn = new JButton("Shutdown " + name);
		panel.add(bttn);
		panel.add(Box.createRigidArea(new Dimension(25, 0)));
		
		JButton bttn2 = new JButton("Prevent " + name);
		panel.add(bttn2);
		panel.add(Box.createRigidArea(new Dimension(25, 0)));
		
		JButton bttn3 = new JButton("Add Item - " + name);
		panel.add(bttn3);
		panel.add(Box.createRigidArea(new Dimension(25, 0)));
		
		JButton bttn4 = new JButton("Remove Item - " + name);
		panel.add(bttn4);
		panel.add(Box.createRigidArea(new Dimension(25, 0)));
		
		JButton bttn5 = new JButton("Unsupervise " + name);
		panel.add(bttn5);
		panel.add(Box.createRigidArea(new Dimension(25, 0)));
		
		bttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	CheckoutController checkoutController = NetworkController.getCheckoutStationController(name);
        		if (checkoutController != null) {
        			if (bttn.getText().equals("Shutdown " + name)) {
        				shutdownController.shutdownStation(checkoutController.getSelfCheckoutStation(), false);
        				shutdownStations.add(name);
            			bttn.setText("Reboot " + name);
            			bttn2.setEnabled(false);
            			bttn3.setEnabled(false);
            			bttn4.setEnabled(false);
            			updateScreen();
        			}
        			else if (bttn.getText().equals("Reboot " + name)) {
//        				NetworkController.removeCheckoutStation(name);
        				CheckoutController newController = startupController.runsStartUpRoutine(checkoutController.getSelfCheckoutStation(), true);
        				NetworkController.registerCheckoutStation(name, newController);
        				shutdownStations.remove(name);
            			bttn.setText("Shutdown " + name);
            			bttn2.setEnabled(true);
            			bttn3.setEnabled(true);
            			bttn4.setEnabled(true);
            			updateScreen();
        			}
        		}
            }
        });
		
		//Permit Station and Prevent Station Implementation doesn't do anything
		bttn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	CheckoutController checkoutController = NetworkController.getCheckoutStationController(name);
            	PreventStation prevent = new PreventStation();
        		if (checkoutController != null) {
        			if (bttn2.getText().equals("Prevent " + name)) {
//        				prevent.suspend();
        				bttn2.setText("Permit " + name);
        			}
        			else if (bttn2.getText().equals("Permit " + name)) {
        				bttn2.setText("Prevent " + name);
            			updateScreen();
        			}
        		}
            }
        });
		
		bttn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		dlgSearchProduct test = new dlgSearchProduct(null, null);
        		test.setVisible(true);
        		if (test.selectedItemCode != null) {
        			
        		}
            }
        });
		
		bttn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	CheckoutController checkoutController = NetworkController.getCheckoutStationController(name);
        		if (checkoutController != null) {
        			unsupervisedStations.add(name);
        			attendantStation.remove(checkoutController.getSelfCheckoutStation());
        			removeStation(panel, separator);
        		}
            }
        });
				
		if (shutdownStations.contains(name)) {
			bttn.doClick();
		}
		
		Component temp[] = {panel, separator};
		return temp;
	}
	
	public JPanel getNotificationPanels() {
		JPanel panel = new JPanel();
		
		JLabel lblNewLabel_1 = new JLabel("Notification");
		panel.add(lblNewLabel_1);
		panel.add(Box.createRigidArea(new Dimension(100, 0)));
		
		JButton addStationButton = new JButton("Allow");
		panel.add(addStationButton);
		panel.add(Box.createRigidArea(new Dimension(25, 0)));
		
		JButton btnNewButton_2 = new JButton("Deny");
		panel.add(btnNewButton_2);
		
		return panel;
	}
}