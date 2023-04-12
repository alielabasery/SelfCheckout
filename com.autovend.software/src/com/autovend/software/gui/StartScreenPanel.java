/** 
* Group Members: 
* 
* Ella Tomlinson (30140549)
* Kofi Frempong (30054189) 
* Adam Beauferris (30056865) 
* Niran Malla (30086877)
* Owen Tinning (30102041)
* Victor Campos Goitia (30106934)
* Zoe Kirsman (30113704) 
* Youssef Abdelrhafour (30085837) 
* James Rettie (30123362) 
* Rezwan Ahmed (30134609)
* Angeline Tran (30139846) 
* Saad Elkadri (30089084) 
* Dante Kirsman (30120778) 
* Riyad Abdullayev (30140509)
* Saksham Puri (30140617) 
* Faisal Islam (30140826)
* Naheen Kabir (30142101) 
* Jose Perales Rivera (30143354) 
* Aditi Yadav (30143652)
* Sahaj Malhotra () 
* Ali Elabasery (30148424)
* Fabiha Fairuzz Subha (30148674) 
* Umesh Oad (30152293)
* Daniel Boettcher (30153811) 
* Nam Nguyen Vu (30154892)
* 
*/
package com.autovend.software.gui;

import com.autovend.software.controllers.CheckoutGUIController;
import com.autovend.software.controllers.GuiController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreenPanel extends JPanel {
	CheckoutGUIController gc;
    JButton button;
    JButton membershipButton;
    JLabel welcomeLabel, startLabel, stationNameLabel;
    
    public StartScreenPanel(CheckoutGUIController gc) {
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
        
        stationNameLabel = new JLabel(gc.getCheckout().getStationName());
        stationNameLabel.setBounds(20, 650, 300, 50);
        stationNameLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        add(stationNameLabel);
    }
}
