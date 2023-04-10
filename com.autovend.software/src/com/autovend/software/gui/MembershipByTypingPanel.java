package com.autovend.software.gui;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.autovend.devices.SimulationException;
import com.autovend.software.controllers.GuiController;
import com.autovend.software.controllers.MembershipCardController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class MembershipByTypingPanel extends JPanel{
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
    String userID;
    Scanner sc;

    
    public MembershipByTypingPanel() {
        MembershipByTypingFrame = new JFrame();
        MembershipByTypingPanel = new JPanel();
        MembershipByTypingPanel.setPreferredSize(new Dimension(1280, 720));
        MembershipByTypingPanel.setLayout(null);

        IDField = new JTextField();
        IDField.setBounds(540, 325, 200, 100);
        IDLabel = new JLabel("Membership Number:");
        IDLabel.setBounds(487, 323, 20, 20);

        MembershipByTypingLabel = new JLabel("Please enter your membership below:");
        MembershipByTypingLabel.setBounds(380, 200, 500, 150);
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

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userID = IDField.getText();
                sc = new Scanner(System.in);
                int i = sc.nextInt();
                String m = "123456789012";
                try {
                    MembershipCardController.getValidMembershipNumberByTyping(m);
                }
                catch(SimulationException s) {
                    IDField.setText("");
                    add(failLabel);
                    revalidate();
                    repaint();
                }   
            }
        });
        
        // initialization moved to here
        userID = IDField.getText();
     //   sc = new Scanner(System.in);
        String m = "123456789012";
        String validMembershipNumber = MembershipCardController.getValidMembershipNumberByTyping(m);

        add(MembershipByTypingLabel);
        add(IDField);
        add(IDLabel);
        add(button);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Membership By Typing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        MembershipByTypingPanel panel = new MembershipByTypingPanel();
        frame.add(panel);
        frame.setVisible(true);
    }
}