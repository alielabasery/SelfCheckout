package com.autovend.software.attendantgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class AttendantPanel {
	
    JFrame attendantFrame;
    JPanel attendantPanel;
    
    int stationCounter = 0;
    
	public AttendantPanel(JFrame prevFrame, JPanel prevPane) {
		attendantFrame = prevFrame;
		attendantFrame.setTitle("Supervising Station");
		
		attendantPanel = prevPane;
		attendantPanel.setPreferredSize(new Dimension(1280, 720));
		attendantPanel.setLayout(null);
		
		Panel stationsPanel = new Panel();
		stationsPanel.setBounds(469, 52, 801, 587);
		stationsPanel.setLayout(new BorderLayout(0, 0));
		attendantPanel.add(stationsPanel);
		
		Box stationsBox = Box.createVerticalBox();
		JScrollPane stationsScrollPane = new JScrollPane(stationsBox);
		stationsScrollPane.setPreferredSize(new Dimension(801, 587));
		stationsPanel.add(stationsScrollPane, BorderLayout.NORTH);
		
		stationsBox.add(getStationPanels());
		stationsBox.add(new JSeparator());

		for (int i = 0; i < 3; i++) {
			stationsBox.add(getStationPanels());
			stationsBox.add(new JSeparator());
		}
		
		JLabel lblNewLabel = new JLabel("Supervised Stations");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(469, 10, 183, 33);
		attendantPanel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Logout");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(1185, 20, 85, 21);
		attendantPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Add New Station");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_1.setBounds(801, 661, 156, 27);
		attendantPanel.add(btnNewButton_1);
		
		JLabel lblNotifications = new JLabel("Notifications");
		lblNotifications.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNotifications.setBounds(10, 10, 183, 33);
		attendantPanel.add(lblNotifications);
		
		Panel notificationsPanel = new Panel();
		notificationsPanel.setBounds(10, 52, 453, 587);
		notificationsPanel.setLayout(new BorderLayout(0, 0));
		attendantPanel.add(notificationsPanel);
		
		Box notificationsBox = Box.createVerticalBox();
		JScrollPane notificationsScrollPanel = new JScrollPane(notificationsBox);
		notificationsScrollPanel.setPreferredSize(new Dimension(801, 587));
		notificationsPanel.add(notificationsScrollPanel, BorderLayout.NORTH);
		
		for (int i = 0; i < 2; i++) {
			notificationsBox.add(getNotificationPanels());
			notificationsBox.add(new JSeparator());
		}
		
        show();
	}
	
	public JPanel getStationPanels() {
		JPanel panel = new JPanel();
		
		JLabel lblNewLabel_1 = new JLabel("Station: " + stationCounter);
		panel.add(lblNewLabel_1);
		panel.add(Box.createRigidArea(new Dimension(100, 0)));
		
		JButton btnNewButton_1 = new JButton("Button 1");
		panel.add(btnNewButton_1);
		panel.add(Box.createRigidArea(new Dimension(25, 0)));
		
		JButton btnNewButton_2 = new JButton("Button 2");
		panel.add(btnNewButton_2);
		panel.add(Box.createRigidArea(new Dimension(25, 0)));
		
		JButton btnNewButton_3 = new JButton("Button 3");
		panel.add(btnNewButton_3);
				
		stationCounter++;
		
		return panel;
	}
	
	public JPanel getNotificationPanels() {
		JPanel panel = new JPanel();
		
		JLabel lblNewLabel_1 = new JLabel("Notification");
		panel.add(lblNewLabel_1);
		panel.add(Box.createRigidArea(new Dimension(100, 0)));
		
		JButton btnNewButton_1 = new JButton("Allow");
		panel.add(btnNewButton_1);
		panel.add(Box.createRigidArea(new Dimension(25, 0)));
		
		JButton btnNewButton_2 = new JButton("Deny");
		panel.add(btnNewButton_2);
		
		return panel;
	}
	
    public void show(){
    	attendantFrame.getContentPane().add(attendantPanel, BorderLayout.CENTER);
		attendantFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		attendantFrame.pack();
		attendantFrame.setVisible(true);
        attendantFrame.setResizable(false);        
    }
}
