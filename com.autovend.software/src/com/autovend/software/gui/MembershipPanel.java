package com.autovend.software.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.autovend.software.controllers.CheckoutController;
import com.autovend.software.controllers.GuiController;

public class MembershipPanel extends JPanel {
    GuiController gc;

    JButton scanningButton;
    JButton swipingButton;
    JButton typingButton;
    JButton goToHomeButton;
    JLabel membershipText;
    public MembershipPanel(GuiController gc, CheckoutController controller) {
        this.gc = gc;

        setPreferredSize(new Dimension(1280, 720));
        setLayout(null);

        scanningButton = new JButton("Scan Membership");
        scanningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { gc.membershipDetailsScreen(controller); }
        });

        scanningButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        scanningButton.setForeground(Color.BLACK);
        scanningButton.setBorder(new LineBorder(Color.BLACK, 2, true));
        scanningButton.setBounds(470, 270, 350, 50);
        scanningButton.setOpaque(true);
        scanningButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(scanningButton);

        typingButton = new JButton("Enter Membership");
        typingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { gc.membershipDetailsScreen(controller); }
        });

        typingButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        typingButton.setForeground(Color.BLACK);
        typingButton.setBorder(new LineBorder(Color.BLACK, 2, true));
        typingButton.setBounds(470, 330, 350, 50);
        typingButton.setOpaque(true);
        typingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(typingButton);

        swipingButton = new JButton("Swipe Membership");
        swipingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { gc.membershipDetailsScreen(controller); }
        });

        swipingButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        swipingButton.setForeground(Color.BLACK);
        swipingButton.setBorder(new LineBorder(Color.BLACK, 2, true));
        swipingButton.setBounds(470, 390, 350, 50);
        swipingButton.setOpaque(true);
        swipingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(swipingButton);

        goToHomeButton = new JButton("Go back home");
        goToHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { gc.startScreen(controller); }
        });

        goToHomeButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        goToHomeButton.setForeground(Color.BLACK);
        goToHomeButton.setBorder(new LineBorder(Color.BLACK, 2, true));
        goToHomeButton.setBounds(470, 450, 350, 50);
        goToHomeButton.setOpaque(true);
        goToHomeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(goToHomeButton);

        membershipText = new JLabel("Membership");
        membershipText.setBounds(420, 100, 500, 150);
        membershipText.setFont(new Font(Font.MONOSPACED, Font.BOLD, 75));
        add(membershipText);
    }
}
