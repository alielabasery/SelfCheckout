package com.autovend.software.gui;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.autovend.IllegalDigitException;
import com.autovend.devices.BarcodeScanner;
import com.autovend.devices.SimulationException;
import com.autovend.software.controllers.GuiController;
import com.autovend.software.controllers.MembershipCardController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class MembershipByScanningPanel extends JPanel {
	MembershipCardController mcc;
	GuiController gc;
	JLabel MembershipByScanningLabel;
	JTextField IDField;
	JLabel IDLabel;
	JButton button;
	JLabel failLabel;
	String userID;
	Scanner sc;
	BarcodeScanner barcodeScanner = new BarcodeScanner();
	

	// Scan your membership
	// Button that allows you to scan
	// Go back

	public MembershipByScanningPanel(GuiController gc, MembershipCardController mcc) {
		this.gc = gc;
		setPreferredSize(new Dimension(1280, 720));
		setLayout(null);

		MembershipByScanningLabel = new JLabel("Scan your membership");
		MembershipByScanningLabel.setBounds(450, 150, 750, 150);
		MembershipByScanningLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));

		button = new JButton("Connect");
		button.setBackground(Color.decode("#ade89b"));
		button.setForeground(Color.BLACK);
		button.setBorder(new LineBorder(Color.BLACK, 1, true));
		button.setBounds(590, 300, 100, 20);
		button.setOpaque(true);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));

		failLabel = new JLabel("The membership number scanned was invalid");
		failLabel.setBounds(510, 410, 300, 20);
		failLabel.setForeground(Color.RED);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String m = "123456789012";
				try {
					Boolean success = MembershipCardController.isValid(userID);
					if (success) {
						gc.addItemsScreen();
					}
					remove(failLabel);
					revalidate();
					repaint();
				} catch (IllegalDigitException s) {
					IDField.setText("");
					add(failLabel);
					revalidate();
					repaint();
				}
			}
		});
		add(MembershipByScanningLabel);
        add(IDField);
        add(IDLabel);
        add(button);

	}

}
