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
        setLayout(null);
  
        MembershipByTypingLabel = new JLabel("Please enter your membership below:");
        MembershipByTypingLabel.setBounds(450, 150, 750, 150);
        MembershipByTypingLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        IDField = new JTextField();
        IDField.setBounds(540,200, 200, 100);
        IDField.setHorizontalAlignment(JTextField.CENTER); // set the horizontal alignment
        IDLabel = new JLabel("Membership Number:");
        IDField.setBounds(540,270, 200, 25);
        IDField.setHorizontalAlignment(JLabel.CENTER); // set the horizontal alignment

        button = new JButton("Connect");
        button.setBackground(Color.decode("#ade89b"));
        button.setForeground(Color.BLACK);
        button.setBorder(new LineBorder(Color.BLACK, 1, true));
        button.setBounds(590, 300, 100, 20);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        failLabel = new JLabel("The membership number entered was invalid");
        failLabel.setBounds(510, 410, 300, 20);
        failLabel.setForeground(Color.RED);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userID = IDField.getText();
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