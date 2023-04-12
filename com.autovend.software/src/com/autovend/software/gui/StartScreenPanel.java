package com.autovend.software.gui;

import com.autovend.software.controllers.GuiController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreenPanel extends JPanel {
    GuiController gc;
    JButton button;
    JButton membershipButton;
    JLabel welcomeLabel;
    JLabel startLabel;
    public StartScreenPanel(GuiController gc) {
        this.gc = gc;

        setPreferredSize(new Dimension(1280, 720));
        setLayout(null);

        button = new JButton("Start");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gc.addItemsScreen();
            }
        });

        button.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        button.setBackground(Color.decode("#009b00"));
        button.setForeground(Color.BLACK);
        button.setBorder(new LineBorder(Color.BLACK, 2, true));
        button.setBounds(470, 450, 350, 100);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(button);

        membershipButton = new JButton("Membership");
        membershipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gc.membershipScreen();
            }
        });

        membershipButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        membershipButton.setForeground(Color.BLACK);
        membershipButton.setBorder(new LineBorder(Color.BLACK, 2, true));
        membershipButton.setBounds(470, 570, 350, 50);
        membershipButton.setOpaque(true);
        membershipButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(membershipButton);

        welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setBounds(470, 200, 500, 150);
        welcomeLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 75));
        add(welcomeLabel);

        startLabel = new JLabel("Press start to begin!");
        startLabel.setBounds(480, 280, 500, 150);
        startLabel.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 26));
        add(startLabel);
    }
}
