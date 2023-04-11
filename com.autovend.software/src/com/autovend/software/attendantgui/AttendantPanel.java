package com.autovend.software.attendantgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import com.autovend.devices.SimulationException;
import com.autovend.devices.SupervisionStation;
import com.autovend.devices.SelfCheckoutStation;
import com.autovend.software.controllers.AttendentController;
import com.autovend.software.controllers.CheckoutController;
import com.autovend.software.controllers.GuiController;

import Networking.NetworkController;

public class AttendantPanel extends JPanel {
	GuiController gc;
	SupervisionStation attendantStation;
	AttendentController attendantController;
	
	private Currency currency;
	private int[] billDenominations;
	private BigDecimal[] coinDenominations;
	
	private int scaleMaximumWeight = 1000;
	private int scaleSensitivity = 100;
	
    int stationCounter = 0;
        
	public AttendantPanel(GuiController gc, SupervisionStation attendantStation, AttendentController attendantController) {
		this.gc = gc;
		this.attendantStation = attendantStation;
		this.attendantController = attendantController;
		
    	currency = Currency.getInstance("CAD");
		billDenominations = new int[] {5, 10, 20, 50, 100};
		coinDenominations = new BigDecimal[] {new BigDecimal(5), new BigDecimal(10), new BigDecimal(25), new BigDecimal(100)};
		
		setPreferredSize(new Dimension(1280, 720));
		setLayout(null);
		
		Panel stationsPanel = new Panel();
		stationsPanel.setBounds(469, 52, 801, 587);
		stationsPanel.setLayout(new BorderLayout(0, 0));
		add(stationsPanel);
		
		Box stationsBox = Box.createVerticalBox();
		JScrollPane stationsScrollPane = new JScrollPane(stationsBox);
		stationsScrollPane.setPreferredSize(new Dimension(801, 587));
		stationsPanel.add(stationsScrollPane, BorderLayout.NORTH);
		
		stationsBox.add(getStationPanels());
		stationsBox.add(new JSeparator());

		for (int i = 0; i < attendantStation.supervisedStationCount(); i++) {
			stationsBox.add(getStationPanels());
			stationsBox.add(new JSeparator());
		}
		
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
            	SelfCheckoutStation newStation = new SelfCheckoutStation(
    					currency, billDenominations, coinDenominations, scaleMaximumWeight, scaleSensitivity);
            	CheckoutController checkoutController = new CheckoutController(newStation);
            	NetworkController.registerCheckoutStation(getNextStationName(), checkoutController);
            	attendantStation.add(newStation);
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
	
	public JPanel getStationPanels() {
		JPanel panel = new JPanel();
		
		JLabel label = new JLabel("Station: " + stationCounter);
		panel.add(label);
		panel.add(Box.createRigidArea(new Dimension(100, 0)));
		
		JButton bttn = new JButton("Button 1");
		panel.add(bttn);
		panel.add(Box.createRigidArea(new Dimension(25, 0)));
		
		JButton bttn2 = new JButton("Button 2");
		panel.add(bttn2);
		panel.add(Box.createRigidArea(new Dimension(25, 0)));
		
		JButton bttn3 = new JButton("Button 3");
		panel.add(bttn3);
				
		stationCounter++;
		
		return panel;
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
	
	private String getNextStationName() {
		return "Station: " + ++stationCounter;
	}
}
