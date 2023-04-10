package com.autovend.software.gui;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.autovend.software.controllers.MembershipCardController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MembershipByTypingPanel {
	JFrame MembershipByTypingFrame;
	JPanel MembershipByTypingPanel;
	JLabel MembershipByTypingLabel;
	JTextField IDField;
	JTextField passField;
	JLabel IDLabel;
	JLabel passLabel;
	JButton button;
	JLabel failLabel;

	String testID = "2222";
	MembershipCardController a = new MembershipCardController();

	public MembershipByTypingPanel() {
		MembershipCardController.getValidMembershipNumberByTyping(null);
		MembershipByTypingFrame = new JFrame();
		MembershipByTypingPanel = new JPanel();
		MembershipByTypingPanel.setPreferredSize(new Dimension(1280, 720));
		MembershipByTypingPanel.setLayout(null);

		IDField = new JTextField();
		IDField.setBounds(540, 325, 200, 20);
		IDLabel = new JLabel("Membership Number:");
		IDLabel.setBounds(487, 323, 100, 20);

		MembershipByTypingLabel = new JLabel("Please enter your membership below:");
		MembershipByTypingLabel.setBounds(400, 200, 500, 150);
		MembershipByTypingLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));

		button = new JButton("Connect");
		button.setBackground(Color.decode("#ade89b"));
		button.setForeground(Color.BLACK);
		button.setBorder(new LineBorder(Color.BLACK, 1, true));
		button.setBounds(590, 450, 100, 20);
		button.setOpaque(true);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));

		failLabel = new JLabel("The membership number entered was invalid");
		failLabel.setBounds(510, 410, 300, 20);
		failLabel.setForeground(Color.RED);
		
		
		
		
		
		
	}
}
