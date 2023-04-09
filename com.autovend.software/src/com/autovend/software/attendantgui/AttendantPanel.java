package com.autovend.software.attendantgui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AttendantPanel {
	
    JFrame attendantFrame;
    JPanel attendantPanel;
	
    JLabel IDLabel;
    JLabel passLabel;
    
	public AttendantPanel(JFrame prevFrame) {
		attendantFrame = prevFrame;
		attendantPanel = new JPanel();
		
		IDLabel = new JLabel("New Screen!!!");
        IDLabel.setBounds(487, 323, 100, 20);

        passLabel = new JLabel("New Screen!!!");
        passLabel.setBounds(475, 373, 100, 20);
		
        show();
	}
	
    public void show(){
    	attendantPanel.add(IDLabel);
    	attendantPanel.add(passLabel);

    	attendantFrame.getContentPane().add(attendantPanel, BorderLayout.CENTER);
		attendantFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		attendantFrame.pack();
		attendantFrame.setVisible(true);
        attendantFrame.setResizable(false);
    }
}
