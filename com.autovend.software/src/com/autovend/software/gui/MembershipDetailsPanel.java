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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.autovend.IllegalDigitException;
import com.autovend.software.controllers.CheckoutController;
import com.autovend.software.controllers.GuiController;
import com.autovend.software.controllers.MembershipCardController;

public class MembershipDetailsPanel extends JPanel{
    MembershipCardController mcc;
    GuiController gc;
    JLabel membershipByTypingLabel;
    JTextField IDField;
    JLabel IDLabel;
    JButton button;
    JButton backButton;
    JLabel failLabel;
    String userID;

    
    public MembershipDetailsPanel(GuiController gc, MembershipCardController mcc, CheckoutController controller) {
        this.gc = gc;
        this.mcc = mcc;

        setPreferredSize(new Dimension(1280, 720));
        setLayout(null);

        membershipByTypingLabel = new JLabel("Please enter your membership below:");
        membershipByTypingLabel.setBounds(450, 150, 750, 150);
        membershipByTypingLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        IDField = new JTextField();
        IDField.setBounds(540, 200, 200, 100);
        IDField.setHorizontalAlignment(JTextField.CENTER); // set the horizontal alignment
        IDLabel = new JLabel("Membership Number:");
        IDField.setBounds(540, 270, 200, 25);
        IDField.setHorizontalAlignment(JLabel.CENTER); // set the horizontal alignment

        button = new JButton("Connect");
        button.setBackground(Color.decode("#ade89b"));
        button.setForeground(Color.BLACK);
        button.setBorder(new LineBorder(Color.BLACK, 1, true));
        button.setBounds(590, 300, 100, 20);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        backButton = new JButton("Back");
        backButton.setBounds(590, 330, 100, 20);
        backButton.setBackground(Color.decode("#ade89b"));
        backButton.setForeground(Color.BLACK);
        backButton.setBorder(new LineBorder(Color.BLACK, 1, true));
        backButton.setOpaque(true);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gc.membershipScreen(controller);
            }
        });

        add(backButton);

        failLabel = new JLabel("The membership number entered was invalid");
        failLabel.setBounds(510, 410, 300, 20);
        failLabel.setForeground(Color.RED);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userID = IDField.getText();
                try {
                    Boolean success = mcc.isValid(userID);
                    if (success) { gc.addItemsScreen(controller); }
                    remove(failLabel);
                    revalidate();
                    repaint();
                } catch (IllegalDigitException s) {
                    IDField.setText("");
                    add(failLabel);
                    revalidate();
                    repaint();
                }
            }
        });

        add(membershipByTypingLabel);
        add(IDField);
        add(IDLabel);
        add(button);
    }
}