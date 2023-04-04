package com.autovend.software.gui;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreenPanel {
    public StartScreenPanel() {
        JFrame startFrame = new JFrame();

        JPanel startPanel = new JPanel();
        startPanel.setPreferredSize(new Dimension(1280, 720));
        startPanel.setLayout(null);

        JButton button = new JButton("Start");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Change to checkoutPanel here
            }
        });

        button.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        button.setBackground(Color.decode("#009b00"));
        button.setForeground(Color.BLACK);
        button.setBorder(new LineBorder(Color.BLACK, 2, true));
        button.setBounds(470, 450, 350, 100);
        button.setOpaque(true);
        startPanel.add(button);

        JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setBounds(470, 200, 500, 150);
        welcomeLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 75));
        startPanel.add(welcomeLabel);

        JLabel startLabel = new JLabel("Press start to begin!");
        startLabel.setBounds(480, 280, 500, 150);
        startLabel.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 26));
        startPanel.add(startLabel);

        startFrame.getContentPane().add(startPanel, BorderLayout.CENTER);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.pack();
        startFrame.setVisible(true);
        startFrame.setResizable(false);
    }

    // Below is only for visualization, delete later.
    public static void main(String[] args) {
        new StartScreenPanel();
    }
}
