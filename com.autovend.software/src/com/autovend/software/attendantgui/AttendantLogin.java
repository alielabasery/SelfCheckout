package com.autovend.software.attendantgui;

import com.autovend.devices.SimulationException;
import com.autovend.software.controllers.AttendantLoginLogoutController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttendantLogin {
    JFrame loginFrame;
    JPanel loginPanel;
    JLabel loginLabel;
    JTextField IDField;
    JTextField passField;
    JLabel IDLabel;
    JLabel passLabel;
    JButton button;
    JLabel failLabel;
    private static final String attendant1Id = "001";
    private static final String attendant1Password = "Attendant1!";

    AttendantLoginLogoutController a = new AttendantLoginLogoutController();

    public AttendantLogin() {
        AttendantLoginLogoutController.idAndPasswords.put(attendant1Id, attendant1Password);

        loginFrame = new JFrame();
        loginPanel = new JPanel();
        loginPanel.setPreferredSize(new Dimension(1280, 720));
        loginPanel.setLayout(null);

        IDField = new JTextField();
        IDField.setBounds(540,325,200,20);

        passField = new JTextField();
        passField.setBounds(540, 375, 200, 20);

        IDLabel = new JLabel("UserID:");
        IDLabel.setBounds(487, 323, 100, 20);

        passLabel = new JLabel("Password:");
        passLabel.setBounds(475, 373, 100, 20);

        loginLabel = new JLabel("Please enter your userID and password below:");
        loginLabel.setBounds(400, 200, 500, 150);
        loginLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));

        button = new JButton("Submit");
        button.setBackground(Color.decode("#ade89b"));
        button.setForeground(Color.BLACK);
        button.setBorder(new LineBorder(Color.BLACK, 1, true));
        button.setBounds(590, 450, 100, 20);
        button.setOpaque(true);

        failLabel = new JLabel("The userID or password entered was invalid.");
        failLabel.setBounds(510, 410, 300, 20);
        failLabel.setForeground(Color.RED);

        // On submit
        button.addActionListener(e -> {
            String userID = IDField.getText();
            String password = passField.getText();
            // Run the AttendantLogin method from the AttendantLoginLogoutController
            try {
                a.AttendantLogin(userID, password);
                // If login information passes, go to attendant panel
                // TODO: Go to main attendant access panel
            }
            catch (SimulationException s){
                // If login failed
                IDField.setText("");
                passField.setText("");
                loginPanel.add(failLabel);
                show();
            }

        });
        loginPanel.add(loginLabel);
        loginPanel.add(IDField);
        loginPanel.add(passField);
        loginPanel.add(IDLabel);
        loginPanel.add(passLabel);
        loginPanel.add(button);

        loginFrame.getContentPane().add(loginPanel, BorderLayout.CENTER);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.pack();
        loginFrame.setVisible(true);
        loginFrame.setResizable(false);

    }

    public void show(){
        loginPanel.add(loginLabel);
        loginPanel.add(IDField);
        loginPanel.add(passField);
        loginPanel.add(IDLabel);
        loginPanel.add(passLabel);
        loginPanel.add(button);

        loginFrame.getContentPane().add(loginPanel, BorderLayout.CENTER);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.pack();
        loginFrame.setVisible(true);
        loginFrame.setResizable(false);
    }

    // Below is only for visualization, delete later.
    public static void main(String[] args) {
        new AttendantLogin();
    }
}
