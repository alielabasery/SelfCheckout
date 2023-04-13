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
* Sahaj Malhotra (30144405) 
* Ali Elabasery (30148424)
* Fabiha Fairuzz Subha (30148674) 
* Umesh Oad (30152293)
* Daniel Boettcher (30153811) 
* Nam Nguyen Vu (30154892)
* 
*/

package com.autovend.software.attendantgui;

import com.autovend.devices.SimulationException;
import com.autovend.software.controllers.AttendantLoginLogoutController;
import com.autovend.software.controllers.GuiController;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttendantLogin extends JPanel {
    GuiController gc;
    JLabel loginLabel;
    JTextField IDField;
    JPasswordField passField;
    JLabel IDLabel;
    JLabel passLabel;
    JButton button;
    JLabel failLabel;
    private static final String attendant1Id = "001";
    private static final String attendant1Password = "Attendant1!";

    AttendantLoginLogoutController a;

    public AttendantLogin(GuiController gc, AttendantLoginLogoutController a) {
        this.gc = gc;
        this.a = a;

        AttendantLoginLogoutController.idAndPasswords.put(attendant1Id, attendant1Password);

        setPreferredSize(new Dimension(1280, 720));
        setLayout(null);

        IDField = new JTextField();
        IDField.setBounds(540,325,200,20);

        passField = new JPasswordField();
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
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        failLabel = new JLabel("The userID or password entered was invalid.");
        failLabel.setBounds(510, 410, 300, 20);
        failLabel.setForeground(Color.RED);

        // On submit
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userID = IDField.getText();
                String password = String.valueOf(passField.getPassword());
                try {
                    // If login information passes, got to attendant panel
                    a.AttendantLogin(userID, password);
                    gc.attendantScreen();
                } catch (SimulationException s) {
                    // If login failed
                    IDField.setText("");
                    passField.setText("");
                    add(failLabel);
                    revalidate();
                    repaint();
                }
            }
        });
        add(loginLabel);
        add(IDField);
        add(passField);
        add(IDLabel);
        add(passLabel);
        add(button);
    }
}