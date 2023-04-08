package com.autovend.software.attendantgui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttendantLogin {
    public AttendantLogin() {
        JFrame startFrame = new JFrame();

        JPanel startPanel = new JPanel();
        startPanel.setPreferredSize(new Dimension(1280, 720));
        startPanel.setLayout(null);

        startFrame.getContentPane().add(startPanel, BorderLayout.CENTER);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.pack();
        startFrame.setVisible(true);
        startFrame.setResizable(false);
    }

    // Below is only for visualization, delete later.
    public static void main(String[] args) {
        new AttendantLogin();
    }
}
